package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/**
 * Created by Ferdinand on 12/09/2017.
 */

@Entity(foreignKeys = {@ForeignKey(entity = Manual.class, parentColumns = "id", childColumns = "manualId")}, primaryKeys = {"manualId"})
public class ManualFileInfo extends FileInfo {

    @NonNull
    private Long manualId;

    public ManualFileInfo(Long manualId) {
        super(manualId.toString());
        this.manualId = manualId;
    }

    public Long getManualId() {
        return manualId;
    }

    public void setManualId(Long manualId) {
        this.manualId = manualId;
    }
}
