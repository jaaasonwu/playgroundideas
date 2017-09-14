package com.playgroundideas.playgroundideas.datasource;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.playgroundideas.playgroundideas.datasource.local.FileStorage;
import com.playgroundideas.playgroundideas.datasource.local.ManualDao;
import com.playgroundideas.playgroundideas.datasource.remote.ManualWebservice;
import com.playgroundideas.playgroundideas.model.Manual;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    public LiveData<List<Manual>> getDownloaded() {
        LiveData<List<Manual>> liveData = manualDao.loadAll();
        return Transformations.map(liveData, new Function<List<Manual>, List<Manual>>() {
            @Override
            public List<Manual> apply(List<Manual> manuals) {
                List<Manual> downloadedManuals = new LinkedList<>();
                for(Manual m : manuals) {
                    if(FileStorage.isDownloaded(m.getPdfInfo())) {
                        downloadedManuals.add(m);
                    }
                }
                return downloadedManuals;
            }
        });
    }

    public void downloadManual(Manual manual) {
        // TODO download manual using webservice
        //FileStorage.writeManualFile(manual.getPdfInfo(), tempFile);
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
