package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.repository.ProjectRepository;
import com.playgroundideas.playgroundideas.model.Project;
import com.playgroundideas.playgroundideas.model.ProjectPictureFileInfo;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 13/09/2017.
 */

public class ProjectViewModel extends ViewModel {

    private LiveData<Project> project;
    private LiveData<List<ProjectPictureFileInfo>> pictures;
    private ProjectRepository projectRepository;

    @Inject
    public ProjectViewModel(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void init(Long projectId) {
        if(project != null || pictures != null) {
            return;
        } else {
            project = projectRepository.getProject(projectId);
            pictures = projectRepository.getPicturesOf(projectId);
        }
    }

    public LiveData<Project> getProject() {
        return this.project;
    }
}
