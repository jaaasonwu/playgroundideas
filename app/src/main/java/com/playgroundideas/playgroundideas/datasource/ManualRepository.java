package com.playgroundideas.playgroundideas.datasource;

import android.arch.lifecycle.LiveData;

import com.playgroundideas.playgroundideas.datasource.local.ManualDao;
import com.playgroundideas.playgroundideas.datasource.remote.ManualWebservice;
import com.playgroundideas.playgroundideas.domain.Manual;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Ferdinand on 9/09/2017.
 */

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

    public LiveData<Manual> getManual(long id) {
        refreshManual(id);
        // return a LiveData directly from the database.
        return manualDao.load(id);
    }

    private void refreshManual(final long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if user was fetched recently
                boolean userExists = manualDao.hasManual(id);
                if (!userExists) {
                    // TODO implement API response handling (see UserRepository)
                }
            }
        });
    }
}
