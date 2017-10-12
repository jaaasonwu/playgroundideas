package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.lifecycle.LiveData;

import com.playgroundideas.playgroundideas.datasource.local.FileStorage;
import com.playgroundideas.playgroundideas.datasource.local.ManualDao;
import com.playgroundideas.playgroundideas.datasource.remote.ManualWebservice;
import com.playgroundideas.playgroundideas.datasource.remote.NetworkAccess;
import com.playgroundideas.playgroundideas.model.FileInfo;
import com.playgroundideas.playgroundideas.model.Manual;
import com.playgroundideas.playgroundideas.model.ManualChapter;
import com.playgroundideas.playgroundideas.model.ManualFileInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
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
    private final NetworkAccess networkAccess;

    @Inject
    public ManualRepository(ManualWebservice webservice, ManualDao manualDao, Executor executor, NetworkAccess networkAccess) {
        this.webservice = webservice;
        this.manualDao = manualDao;
        this.executor = executor;
        this.networkAccess = networkAccess;
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
        refreshAllManuals();
        return manualDao.loadAll();
    }

    public LiveData<List<ManualChapter>> getManualChaptersOf(Long manualId) {
        refreshManual(manualId);
        return manualDao.loadAllOf(id);
    }

    private void storePDFManualFile(Manual manual, InputStream manualFile) {
        FileInfo info = new FileInfo(manual.getId().toString());
        try {
            FileStorage.writeManualFile(info, manualFile);
            manual.setPdfInfo(info);
            manualDao.update(manual);
        } catch (IOException ioe) {

        }
    }

    private void refreshManual(final Long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if there is a network connection
                if(networkAccess.isNetworkAvailable()) {

                    webservice.getVersion(id).enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            if(response.isSuccessful()) {
                                // check if the date has changed on the server using a version number
                                final long remoteVersion = response.body();
                                final long localVersion = manualDao.getVersionOf(id);

                                if (remoteVersion > localVersion) {
                                    // refresh the local data
                                    // retrieve the data from the remote data source
                                    webservice.getManual(id).enqueue(new Callback<Manual>() {
                                        @Override
                                        public void onResponse(Call<Manual> call, Response<Manual> response) {
                                            if(response.isSuccessful()) {
                                                //create new object from response.body
                                                final Manual newManual = response.body();
                                                // Update the database. The LiveData will automatically refresh so
                                                // we don't need to do anything else here besides updating the database
                                                if (localVersion == 0) {
                                                    manualDao.insert(newManual);
                                                } else {
                                                    manualDao.update(newManual);
                                                }

                                                // don't forget to download the actual manual pdf file
                                                webservice.getPdfOf(newManual.getId()).enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        if(response.isSuccessful()) {
                                                            storePDFManualFile(newManual, response.body().byteStream());
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                                                    }
                                                });

                                                // don't forget to update the manual chapters, too
                                                webservice.getChaptersOf(newManual.getId()).enqueue(new Callback<List<ManualChapter>>() {
                                                    @Override
                                                    public void onResponse(Call<List<ManualChapter>> call, Response<List<ManualChapter>> response) {
                                                        if (response.isSuccessful()) {
                                                            for(ManualChapter chapter : response.body()) {
                                                                manualDao.insertManualChapter(chapter);
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<List<ManualChapter>> call, Throwable throwable) {

                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Manual> call, Throwable throwable) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable throwable) {
                            // do nothing
                        }
                    });
                }
            }
        });
    }

    private void refreshAllManuals() {
        // running in a background thread
        // check if there is a network connection
        if(networkAccess.isNetworkAvailable()) {

            // check if any data has changed on the server
            webservice.getVersionOfAll().enqueue(new Callback<Map<Long, Long>>() {
                @Override
                public void onResponse(Call<Map<Long, Long>> call, Response<Map<Long, Long>> response) {
                    for (Map.Entry<Long, Long> remote : response.body().entrySet()) {
                        final long localVersion = manualDao.getVersionOf(id);
                        if (remote.getValue() > localVersion) {
                            // refresh if data has changed
                            refreshManual(remote.getKey());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map<Long, Long>> call, Throwable throwable) {
                    // do nothing
                }
            });
        }
    }

}
