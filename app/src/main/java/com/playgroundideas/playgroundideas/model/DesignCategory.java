package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public enum DesignCategory {

    //TODO remove
    TEST("test");

    private String description;

    DesignCategory(String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
