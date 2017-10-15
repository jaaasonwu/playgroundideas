package com.playgroundideas.playgroundideas.model;

import android.support.annotation.NonNull;

/**
 * Created by Ferdinand on 12/09/2017.
 */

public class FileInfo {

    @NonNull
    private String name;
    @NonNull
    private String path;

    public FileInfo(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public void setPath(@NonNull String path) {
        this.path = path;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
