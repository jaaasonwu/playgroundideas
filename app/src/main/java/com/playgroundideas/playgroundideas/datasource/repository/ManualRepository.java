package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.lifecycle.LiveData;

import com.playgroundideas.playgroundideas.datasource.local.ManualDao;
import com.playgroundideas.playgroundideas.datasource.remote.ManualWebservice;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.ManualFileInfo;

import org.json.JSONObject;

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

@Singleton
public class ManualRepository {

    private final ManualWebservice webservice;
    private final ManualDao manualDao;
    private final Executor executor;

    @Inject
    public ManualRepository(ManualWebservice webservice, ManualDao manualDao, Executor executor) {
        this.webservice = webservice;
        this.manualDao = manualDao;
        this.executor = executor;
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
        // TODO download manual using webservice
        webservice.getManual(manual.getName());
        //FileStorage.writeManualFile(manual.getPdfInfo(), tempFile);
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
                while (names.hasNext()) {
                    String name = names.next();
                    JSONObject child = jsonObject.getJSONObject(name);
                    long id = child.getLong("id");
                    manuals.add(new Manual(id, name, new ManualFileInfo(id)));
                }
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
}
