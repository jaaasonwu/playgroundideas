package com.playgroundideas.playgroundideas.model;

import android.support.annotation.NonNull;

/**
 * Created by Ferdinand on 12/09/2017.
 */

public class FileInfo {

    @NonNull
    private String name;

    public FileInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
