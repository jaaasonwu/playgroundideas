package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.lifecycle.LiveData;

import com.playgroundideas.playgroundideas.datasource.local.DesignDao;
import com.playgroundideas.playgroundideas.datasource.remote.DesignWebservice;
import com.playgroundideas.playgroundideas.model.Design;

import java.util.List;
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

    public LiveData<Design> get(Long id) {
        refreshDesign(id);
        return designDao.load(id);
    }

    public LiveData<List<Design>> getAllCreatedBy(Long creatorId) {
        LiveData<List<Design>> designsLiveData = designDao.loadAllOf(creatorId);
        return designsLiveData;
    }

    public LiveData<List<Design>> getAll() {
        LiveData<List<Design>> designsLiveData = designDao.loadAll();
        return designsLiveData;
    }

    public LiveData<List<Design>> getFavouritesOf(Long userId) {
        LiveData<List<Design>> favouriteDesigns = designDao.loadFavouritesOf(userId);
        return favouriteDesigns;
    }

    private void refreshDesign(final Long id) {
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
