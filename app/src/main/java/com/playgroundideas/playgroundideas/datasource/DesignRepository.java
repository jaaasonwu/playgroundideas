package com.playgroundideas.playgroundideas.datasource;

import android.arch.lifecycle.LiveData;

import com.playgroundideas.playgroundideas.datasource.local.DesignDao;
import com.playgroundideas.playgroundideas.datasource.remote.DesignWebservice;
import com.playgroundideas.playgroundideas.domain.Design;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Singleton
public class DesignRepository {

    private final DesignWebservice webservice;
    private final DesignDao designDao;
    private final Executor executor;

    @Inject
    public DesignRepository(DesignWebservice webservice, DesignDao designDao, Executor executor) {
        this.webservice = webservice;
        this.designDao = designDao;
        this.executor = executor;
    }

    public LiveData<Design> getDesign(long id) {
        refreshDesign(id);
        // return a LiveData directly from the database.
        return designDao.load(id);
    }

    private void refreshDesign(final long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if user was fetched recently
                boolean designExists = designDao.hasDesign(id);
                if (!designExists) {
                    // TODO implement API response handling (see UserRepository)
                }
            }
        });
    }
}
