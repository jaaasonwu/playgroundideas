package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.playgroundideas.playgroundideas.datasource.local.ProjectDao;
import com.playgroundideas.playgroundideas.datasource.remote.ProjectWebservice;
import com.playgroundideas.playgroundideas.model.Project;
import com.playgroundideas.playgroundideas.model.ProjectPictureFileInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    public LiveData<Project> getProject(Long id) {
        refreshProject(id);
        LiveData<Project> projectLiveData = projectDao.load(id);
        return projectLiveData;
    }

    public void createProject(Project project) {
        projectDao.insertProject(project);
    }

    public void updateProject(Project project) {
        projectDao.update(project);
    }

    public void deleteProject(Project project) {
        projectDao.delete(project);
    }

    public LiveData<List<Project>> getProjects() {
        LiveData<List<Project>> projectsLiveData = projectDao.loadAll();
        return projectsLiveData;
    }

    public LiveData<List<Project>> getAllCreatedBy(Long id) {
        LiveData<List<Project>> projectsLiveData = projectDao.loadAllOf(id);
        return projectsLiveData;
    }

    public LiveData<List<ProjectPictureFileInfo>> getPicturesOf(Long projectId) {
        LiveData<List<ProjectPictureFileInfo>> pictures = projectDao.loadAllPicturesOf(projectId);
        return pictures;
    }

    public LiveData<Map<Long, List<ProjectPictureFileInfo>>> getAllPicturesPerProject() {
        LiveData<List<ProjectPictureFileInfo>> allPictures = projectDao.loadAllPictures();
        LiveData<Map<Long, List<ProjectPictureFileInfo>>> picturesPerProject = Transformations.map(allPictures, new Function<List<ProjectPictureFileInfo>, Map<Long, List<ProjectPictureFileInfo>>>() {
            @Override
            public Map<Long, List<ProjectPictureFileInfo>> apply(List<ProjectPictureFileInfo> projectPictureFileInfos) {
                Map<Long, List<ProjectPictureFileInfo>> map = new HashMap<Long, List<ProjectPictureFileInfo>>();
                for(ProjectPictureFileInfo info : projectPictureFileInfos) {
                    if (!map.containsKey(info.getProjectId())) {
                        map.put(info.getProjectId(), new LinkedList<ProjectPictureFileInfo>());
                    }
                    map.get(info.getProjectId()).add(info);
                }
                return map;
            }
        });
        return picturesPerProject;
    }

    public void addImageToProject(ProjectPictureFileInfo pictureFileInfo) {
        projectDao.insert(pictureFileInfo);
    }

    public void removeImageFromProject(ProjectPictureFileInfo pictureFileInfo) {
        projectDao.delete(pictureFileInfo);
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
