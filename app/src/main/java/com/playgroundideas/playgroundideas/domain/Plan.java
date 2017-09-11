package com.playgroundideas.playgroundideas.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public class Plan {

    @PrimaryKey
    private Long id;
    private String unityFileName;

    public Plan(Long id, String unityFileName) {
        this.id = id;
        this.unityFileName = unityFileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnityFileName() {
        return unityFileName;
    }

    public void setUnityFileName(String unityFileName) {
        this.unityFileName = unityFileName;
    }
}
