package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.ProjectRepository;
import com.playgroundideas.playgroundideas.model.Project;
import com.playgroundideas.playgroundideas.model.ProjectPictureFileInfo;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 13/09/2017.
 */

public class ProjectListViewModel extends ViewModel {

    private LiveData<List<Project>> projectList;
    private LiveData<Map<Long, List<ProjectPictureFileInfo>>> picturesPerProject;
    private ProjectRepository projectRepository;

    @Inject
    public ProjectListViewModel(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /*
     * Calling this method with userId set to null will cause all projects to be loaded
     */
    public void init(Long userId) {
        if (projectList != null || picturesPerProject != null) {
            return;
        } else {
            projectList = (userId != null) ? projectRepository.getAllCreatedBy(userId) : projectRepository.getProjects();
            picturesPerProject = projectRepository.getAllPicturesPerProject();
        }
    }

    public LiveData<List<Project>> getProjectList() {
        return projectList;
    }
}
