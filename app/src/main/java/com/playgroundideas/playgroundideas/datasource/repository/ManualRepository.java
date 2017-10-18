package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.local.ManualDao;
import com.playgroundideas.playgroundideas.datasource.remote.ManualWebservice;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.ManualChapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

@Singleton
public class ManualRepository {

    private final ManualWebservice webservice;
    private final ManualDao manualDao;
    private final Executor executor;
    private final Context context;

    @Inject
    public ManualRepository(ManualWebservice webservice, ManualDao manualDao, Executor executor,
                            Context context) {
        this.webservice = webservice;
        this.manualDao = manualDao;
        this.context = context;
        this.executor = executor;
    }

    /**
     * This method is used to get one manual from the database using id of the manual
     */
    public LiveData<Manual> getManual(Long id) {
        refreshManual(id);
        // return a LiveData directly from the database.
        return manualDao.load(id);
    }

    /**
     * This method is used to get all manuals
     */
    public LiveData<List<Manual>> getAll() {
        // Synchronize database with server every time get all manuals is called
        executor.execute(new Runnable() {
            @Override
            public void run() {
                updateManualInfo();
            }
        });
        return manualDao.loadAll();
    }

    /**
     * This method is used to delete the pdf of a manual and update the database accordingly
     */
    public void deletePdf(final long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Manual delete = manualDao.loadOne(id);
                delete.setDownloaded(false);
                manualDao.update(delete);
            }
        });
    }

    /**
     * This method is used to download a pdf from the server
     */
    public void downloadManual(Manual manual) {
        new DownloadTask().execute(manual);
    }

    private class DownloadTask extends AsyncTask<Manual, String, Boolean> {
        private Handler handler;

        public DownloadTask() {
            handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message input) {
                    String msg = (String) input.obj;
                    Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                    toast.show();
                }
            };
        }

        @Override
        protected Boolean doInBackground(Manual... manuals) {
            long length;
            long count;

            OutputStream out = createOutStream(manuals[0].getName());
            Call<ResponseBody> call = webservice.getManual(manuals[0].getName());
            Response res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (res.isSuccessful()) {
                sendToastMessage(context.getString(R.string.download_start));
                ResponseBody body = (ResponseBody) res.body();
                InputStream input = body.byteStream();
                try {
                    if (input == null) {
                        throw new IOException();
                    }
                } catch (IOException e) {
                    Log.e("Response body", "Cannot create input stream");
                    e.printStackTrace();
                }
                byte data[] = new byte[4096];
                try {
                    while ((count = input.read(data)) != -1) {
                        out.write(data, 0, (int) count);
                    }
                    out.flush();
                    out.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                manuals[0].setDownloaded(true);
                manualDao.update(manuals[0]);
                return true;
            } else {
                sendToastMessage(context.getString(R.string.download_failed));
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            Toast toast = Toast.makeText(context, "Downloaded Finished!", Toast.LENGTH_LONG);
            toast.show();
        }
        private void sendToastMessage(String str) {
            Message msg = Message.obtain();
            msg.obj = str;
            msg.setTarget(handler);
            msg.sendToTarget();
        }

    }

    /**
     * This method is used to create an output stream for writing the downloaded pdf
     */
    private OutputStream createOutStream(String urlStr) {
        File folder = new File(String.valueOf(context.getExternalFilesDir(null)));
        File pdf = new File(folder.getAbsolutePath() + "/" + urlStr + ".pdf");
        OutputStream out = null;

        try {
            if (!folder.exists()) {
                if (!folder.mkdir()) {
                    throw new IOException();
                }
            }
            if (!pdf.exists()) {
                if (!pdf.createNewFile()) {
                    throw new IOException();
                }

            }
            out = new FileOutputStream(pdf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }


    /**
     * Synchronize the database with the server
     */
    public void updateManualInfo() {
        Call<ResponseBody> call = webservice.getInfo();
        try {
            Response<ResponseBody> response = call.execute();

            // Parse the JSON object from the server
            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> names = jsonObject.keys();
            List<ManualChapter> chapters = new ArrayList<>();
            List<Manual> manuals = new ArrayList<>();
            Manual oldManual;

            // Iterate through all manuals
            while (names.hasNext()) {
                String name = names.next();
                JSONObject child = jsonObject.getJSONObject(name);
                long id = child.getLong("id");
                long version = child.getInt("version");

                // Get the old manual object to avoid changing the download state for an already
                // downloaded file
                oldManual = manualDao.loadOne(id);
                Manual insertManual = new Manual(version, id, name, null, null);

                File folder = new File(String.valueOf(context.getExternalFilesDir(null)));
                File pdf = new File(folder.getAbsolutePath() + "/" + name + ".pdf");
                // If no old file in database or an old version of pdf exists, delete the existing
                // pdf file. (for updated database schema)
                if (oldManual == null || (oldManual != null && oldManual.getDownloaded()
                        && oldManual.getVersion() != version)) {
                    if (pdf.exists()) {
                        pdf.delete();
                    }
                }
                // When the same file is already downloaded, set to downloaded
                if (oldManual != null && oldManual.getDownloaded()
                        && oldManual.getVersion() == version) {
                    // Make sure the file exists (avoid user deleting in the file system)
                    if (pdf.exists()) {
                        insertManual.setDownloaded(true);
                    }
                }
                // Update manuals and chapters from JSON
                manuals.add(insertManual);
                JSONArray chapterArray = child.getJSONArray("chapters");
                for (int i = 0; i < chapterArray.length(); i++) {
                    chapters.add(new ManualChapter(i, (String) chapterArray.get(i), id, null, null));
                }
            }
            // Bulk insert to avoid multiple updates of UI
            manualDao.insertManuals(manuals);
            manualDao.insertManualChapters(chapters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshManual(final Long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if user was fetched recently
                boolean userExists = manualDao.hasManual(id);
                if (!userExists) {
                    // TODO implement API call and response handling (see UserRepository)
                }
            }
        });
    }

    public LiveData<List<ManualChapter>> getManualChapters() {
        return manualDao.loadAllChapters();
    }

}
