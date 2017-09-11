package com.playgroundideas.playgroundideas.domain;

/**
 * Created by Ferdinand on 9/09/2017.
 */

public enum DesignCategory {

    TEST("test", "test");

    private Long id;
    private String description;
    private String name;

    DesignCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
