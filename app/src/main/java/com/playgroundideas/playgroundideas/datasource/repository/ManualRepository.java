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
import com.playgroundideas.playgroundideas.manuals.ManualExpandableList;
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

    public LiveData<Manual> getManual(Long id) {
        refreshManual(id);
        // return a LiveData directly from the database.
        return manualDao.load(id);
    }

    public LiveData<List<Manual>> getAll() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                updateManualInfo();
            }
        });
        return manualDao.loadAll();
    }

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

    public void downloadManual(Manual manual) {
        final Manual downloadManual = manual;
        new DownloadTask().execute(manual);
    }

    private class DownloadTask extends AsyncTask<Manual, String, Boolean> {
        private Handler handler;
        ManualExpandableList list;

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
//                sendToast(context.getString(R.string.download_start), list);
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
                length = body.contentLength();
                byte data[] = new byte[4096];
                long total = 0;
                int percentage = 0;
                try {
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        if ((int) ((total * 100) / length) - percentage >= 20) {
                            sendToastMessage("Downloaded: " + percentage + "%");
                            percentage += 20;
                        }

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


    public void updateManualInfo() {
        Call<ResponseBody> call = webservice.getInfo();
        try {
            Response<ResponseBody> response = call.execute();
            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> names = jsonObject.keys();
            List<ManualChapter> chapters = new ArrayList<>();
            List<Manual> manuals = new ArrayList<>();
            Manual oldManual;
            while (names.hasNext()) {
                String name = names.next();
                JSONObject child = jsonObject.getJSONObject(name);
                long id = child.getLong("id");

                // Get the old manual object to avoid changing the download state
                oldManual = manualDao.loadOne(id);
                Manual insertManual = new Manual(id, name, null, null);
                if (oldManual.getDownloaded()) {
                    insertManual.setDownloaded(true);
                }
                manuals.add(insertManual);
                JSONArray chapterArray = child.getJSONArray("chapters");
                for (int i = 0; i < chapterArray.length(); i++) {
                    chapters.add(new ManualChapter(i, (String) chapterArray.get(i), id, null, null));
                }
            }
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
