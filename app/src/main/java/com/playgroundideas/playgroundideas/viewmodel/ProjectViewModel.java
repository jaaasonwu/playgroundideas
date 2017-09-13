package com.playgroundideas.playgroundideas.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.playgroundideas.playgroundideas.datasource.ProjectRepository;
import com.playgroundideas.playgroundideas.model.Project;

import javax.inject.Inject;

/**
 * Created by Ferdinand on 13/09/2017.
 */

public class ProjectViewModel extends ViewModel {

    private LiveData<Project> project;
    private ProjectRepository projectRepository;

    @Inject
    public ProjectViewModel(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void init(Long projectId) {
        if(project != null) {
            return;
        } else {
            project = projectRepository.getProject(projectId);
        }
    }

    public LiveData<Project> getProject() {
        return this.project;
    }
}
