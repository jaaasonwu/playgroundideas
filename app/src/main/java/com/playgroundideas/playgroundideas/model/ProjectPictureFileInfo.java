package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/**
 * Created by Ferdinand on 12/09/2017.
 */

@Entity(foreignKeys = {@ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "projectId")}, primaryKeys = {"projectId", "name"})
public class ProjectPictureFileInfo extends FileInfo {

    @NonNull
    private Long projectId;

    public ProjectPictureFileInfo(String name, String path, Long projectId) {
        super(name, path);
        this.projectId = projectId;
    }

    public ProjectPictureFileInfo(FileInfo info, Long projectId) {
        super(info.getName(), info.getPath());
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
