package com.playgroundideas.playgroundideas.datasource;

import android.arch.lifecycle.LiveData;

import com.playgroundideas.playgroundideas.datasource.local.ProjectDao;
import com.playgroundideas.playgroundideas.datasource.local.ProjectPictureFileDao;
import com.playgroundideas.playgroundideas.datasource.remote.ProjectWebservice;
import com.playgroundideas.playgroundideas.model.Project;
import com.playgroundideas.playgroundideas.model.ProjectPictureFile;

import java.util.List;
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
    private final ProjectPictureFileDao fileDao;
    private final Executor executor;

    @Inject
    public ProjectRepository(ProjectWebservice webservice, ProjectDao projectDao, Executor executor, ProjectPictureFileDao fileDao) {
        this.webservice = webservice;
        this.projectDao = projectDao;
        this.executor = executor;
        this.fileDao = fileDao;
    }

    public LiveData<Project> getProject(Long id) {
        refreshProject(id);
        LiveData<Project> projectLiveData = projectDao.load(id);
        //set the associated picture list
        List<ProjectPictureFile> pictures = fileDao.loadALLOf(id).getValue();
        projectLiveData.getValue().setPictures(pictures);

        return projectLiveData;
    }

    public LiveData<List<Project>> getProjects() {
        LiveData<List<Project>> projectsLiveData = projectDao.loadAll();
        for (Project p : projectsLiveData.getValue()) {
            List<ProjectPictureFile> pictures = fileDao.loadALLOf(p.getId()).getValue();
            p.setPictures(pictures);
        }
        return projectsLiveData;
    }

    public LiveData<List<Project>> getAllCreatedBy(Long id) {
        LiveData<List<Project>> projectsLiveData = projectDao.loadAllOf(id);
        for (Project p : projectsLiveData.getValue()) {
            List<ProjectPictureFile> pictures = fileDao.loadALLOf(p.getId()).getValue();
            p.setPictures(pictures);
        }
        return projectsLiveData;
    }

    private void refreshProject(final Long id) {
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
