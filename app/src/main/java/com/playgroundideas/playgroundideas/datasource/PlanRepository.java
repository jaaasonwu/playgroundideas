package com.playgroundideas.playgroundideas.datasource;

import android.arch.lifecycle.LiveData;

import com.playgroundideas.playgroundideas.datasource.local.PlanDao;
import com.playgroundideas.playgroundideas.datasource.remote.PlanWebservice;
import com.playgroundideas.playgroundideas.domain.Plan;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Singleton
public class PlanRepository {

    private final PlanWebservice webservice;
    private final PlanDao planDao;
    private final Executor executor;

    @Inject
    public PlanRepository(PlanWebservice webservice, PlanDao planDao, Executor executor) {
        this.webservice = webservice;
        this.planDao = planDao;
        this.executor = executor;
    }

    public LiveData<Plan> getPlan(long id) {
        refreshPlan(id);
        // return a LiveData directly from the database.
        return planDao.load(id);
    }

    private void refreshPlan(final long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if user was fetched recently
                boolean planExists = planDao.hasPlan(id);
                if (!planExists) {
                    // TODO implement API response handling (see UserRepository)
                }
            }
        });
    }
}
