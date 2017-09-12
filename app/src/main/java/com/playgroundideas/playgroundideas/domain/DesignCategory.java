package com.playgroundideas.playgroundideas.domain;

import android.arch.persistence.room.Entity;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public enum DesignCategory {

    //TODO remove
    TEST(new Long(0), "test", "test");

    private Long id;
    private String name;
    private String description;

    DesignCategory(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
