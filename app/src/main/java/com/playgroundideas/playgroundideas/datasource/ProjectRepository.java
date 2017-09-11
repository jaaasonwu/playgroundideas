package com.playgroundideas.playgroundideas.datasource;

import android.arch.lifecycle.LiveData;

import com.playgroundideas.playgroundideas.datasource.local.ProjectDao;
import com.playgroundideas.playgroundideas.datasource.remote.ProjectWebservice;
import com.playgroundideas.playgroundideas.domain.Project;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Singleton
public class ProjectRepository {

    private final ProjectWebservice webservice;
    private final ProjectDao projectDao;
    private final Executor executor;

    @Inject
    public ProjectRepository(ProjectWebservice webservice, ProjectDao projectDao, Executor executor) {
        this.webservice = webservice;
        this.projectDao = projectDao;
        this.executor = executor;
    }

    public LiveData<Project> getProject(long id) {
        refreshProject(id);
        // return a LiveData directly from the database.
        return projectDao.load(id);
    }

    private void refreshProject(final long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if user was fetched recently
                boolean projectExists = projectDao.hasProject(id);
                if (!projectExists) {
                    // TODO implement API response handling (see UserRepository)
                }
            }
        });
    }
}
