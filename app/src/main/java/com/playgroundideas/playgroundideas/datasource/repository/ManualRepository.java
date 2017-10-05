package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
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
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;

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
        this.executor = executor;
        this.context = context;
    }

    public LiveData<Manual> getManual(Long id) {
        refreshManual(id);
        // return a LiveData directly from the database.
        return manualDao.load(id);
    }

    public LiveData<List<Manual>> getAll() {
        return manualDao.loadAll();
    }

    public void downloadManual(Manual manual) {
        final Manual downloadManual = manual;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                long length;
                long count;
                Toast toast;

                OutputStream out = createOutStream(downloadManual.getName());
                Call<ResponseBody> call = webservice.getManual(downloadManual.getName());
                Response res = null;
                try {
                    res = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (res.isSuccessful()) {
                    toast = Toast.makeText(context, context.getString(R.string.download_start),
                            Toast.LENGTH_SHORT);
                    toast.show();
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
                                toast = Toast.makeText(context, "Downloaded: " + percentage + "%",
                                        Toast.LENGTH_SHORT);
                                toast.show();
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
                    downloadManual.setDownloaded(true);
                } else {
                    toast = Toast.makeText(context, context.getString(R.string.download_failed), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
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
        call.enqueue(new GetManualInfoCallback(manualDao));
    }

    private class GetManualInfoCallback implements Callback<ResponseBody> {
        private ManualDao mManualDao;
        public GetManualInfoCallback(ManualDao manualDao) {
            mManualDao = manualDao;
        }
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String json = response.body().string();
                JSONObject jsonObject = new JSONObject(json);
                Iterator<String> names = jsonObject.keys();
                List<Manual> manuals = new ArrayList<>();
                List<ManualChapter> chapters;
                while (names.hasNext()) {
                    String name = names.next();
                    JSONObject child = jsonObject.getJSONObject(name);
                    long id = child.getLong("id");
                    manuals.add(new Manual(id, name, null, null));
                    JSONArray chapterArray = child.getJSONArray("chapters");
                    chapters = new ArrayList<>();
                    for (int i = 0; i < chapterArray.length(); i++) {
                        chapters.add(new ManualChapter(i, (String) chapterArray.get(i), id, null, null));
                    }
                    mManualDao.insertManualChapters(chapters);
                }
                mManualDao.insertManuals(manuals);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable throwable) {
            throwable.printStackTrace();
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

    public LiveData<List<ManualChapter>> getManualChaptersOf(Long manualId) {
        return manualDao.loadAllOf(id);
    }

}
