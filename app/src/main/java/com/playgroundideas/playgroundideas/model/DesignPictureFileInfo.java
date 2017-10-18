package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/**
 * Created by Ferdinand on 12/09/2017.
 */

@Entity(foreignKeys = {@ForeignKey(entity = Design.class, parentColumns = "id", childColumns = "designId") }, primaryKeys = {"designId", "name"})
public class DesignPictureFileInfo extends FileInfo {

    @NonNull
    private Long designId;

    public DesignPictureFileInfo(String name, Long designId) {
        super(name);
        this.designId = designId;
    }

    public Long getDesignId() {
        return designId;
    }

    public void setDesignId(Long designId) {
        this.designId = designId;
    }
}
