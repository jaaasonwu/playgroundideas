package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;

import com.playgroundideas.playgroundideas.R;

/**
 * Created by Ferdinand on 2/10/2017.
 */

@Entity
public enum ProjectPostStatus {

    PUBLISH(R.string.project_post_status_publish_description +""), DRAFT(R.string.project_post_status_draft_description+""), PENDING_APPROVAL(R.string.project_post_status_pending_approval_description+""), PAUSED(R.string.project_post_status_paused_description+"");

    private String description;

    ProjectPostStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
