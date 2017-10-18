package com.playgroundideas.playgroundideas.datasource.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.playgroundideas.playgroundideas.datasource.local.FileStorage;
import com.playgroundideas.playgroundideas.datasource.local.ProjectDao;
import com.playgroundideas.playgroundideas.datasource.remote.NetworkAccess;
import com.playgroundideas.playgroundideas.datasource.remote.ProjectWebservice;
import com.playgroundideas.playgroundideas.model.Project;
import com.playgroundideas.playgroundideas.model.ProjectPictureFileInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
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

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Singleton
public class ProjectRepository {

    private final ProjectWebservice webservice;
    private final ProjectDao projectDao;
    private final Executor executor;
    private final NetworkAccess networkAccess;

    @Inject
    public ProjectRepository(ProjectWebservice webservice, ProjectDao projectDao, Executor executor, NetworkAccess networkAccess) {
        this.webservice = webservice;
        this.projectDao = projectDao;
        this.executor = executor;
        this.networkAccess = networkAccess;
    }

    public LiveData<Project> getProject(Long id) {
        refreshProject(id);
        return projectDao.load(id);
    }

    public void createProject(Project project) {
        projectDao.insert(project);
    }

    public void updateProject(Project project) {
        project.increaseVersion();
        projectDao.update(project);
    }

    public void deleteProject(Project project) {
        projectDao.delete(project);
    }

    public LiveData<List<Project>> getProjects() {
        refreshAllProjects();
        LiveData<List<Project>> projectsLiveData = projectDao.loadAll();
        return projectsLiveData;
    }

    public LiveData<List<Project>> getAllCreatedBy(Long id) {
        refreshAllProjects();
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

    private void storeImage(long projectId, String imageId, InputStream image) {
        ProjectPictureFileInfo info = new ProjectPictureFileInfo(imageId, projectId);
        try {
            FileStorage.writeProjectPictureFile(info, image);
            projectDao.insert(info);
        } catch (IOException ioe) {

        }
    }


    private void refreshProject(final Long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // running in a background thread
                // check if there is a network connection
                if(networkAccess.isNetworkAvailable()) {

                    webservice.getVersion(id).enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            // check if the date has changed on the server using a version number
                            final long remoteVersion = response.body();
                            final long localVersion = projectDao.getVersionOf(id);

                            if (remoteVersion > localVersion) {
                                // refresh the local data
                                // retrieve the data from the remote data source
                                webservice.getProject(id).enqueue(new Callback<Project>() {
                                    @Override
                                    public void onResponse(Call<Project> call, Response<Project> response) {
                                        //create new object from response.body
                                        Project project = response.body();
                                        // Update the database. The LiveData will automatically refresh so
                                        // we don't need to do anything else here besides updating the database
                                        if (localVersion == 0) {
                                            projectDao.insert(project);
                                        } else {
                                            projectDao.update(project);
                                        }
                                        final long projectId = project.getId();

                                        // load images of the project
                                        webservice.getImageIdsOf(projectId).enqueue(new Callback<List<String>>() {
                                            @Override
                                            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                                                if(response.isSuccessful()) {
                                                    for(final String imageId : response.body()) {
                                                        webservice.getImage(projectId, imageId).enqueue(new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                if(response.isSuccessful()) {
                                                                    storeImage(projectId, imageId, response.body().byteStream());
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<String>> call, Throwable throwable) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<Project> call, Throwable throwable) {
                                        // do nothing
                                    }
                                });
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

    private void refreshAllProjects() {
        // running in a background thread
        // check if there is a network connection
        if(networkAccess.isNetworkAvailable()) {

            // check if any data has changed on the server
            webservice.getVersionOfAll().enqueue(new Callback<Map<Long, Long>>() {
                @Override
                public void onResponse(Call<Map<Long, Long>> call, Response<Map<Long, Long>> response) {
                    for (Map.Entry<Long, Long> remote : response.body().entrySet()) {
                        final long localVersion = projectDao.getVersionOf(id);
                        if (remote.getValue() > localVersion) {
                            // refresh if data has changed
                            refreshProject(remote.getKey());
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
