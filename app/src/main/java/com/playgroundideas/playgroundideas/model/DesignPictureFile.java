package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ferdinand on 12/09/2017.
 */

@Entity(foreignKeys = {@ForeignKey(entity = Design.class, parentColumns = "id", childColumns = "designId") })
public class DesignPictureFile extends File {

    @PrimaryKey(autoGenerate = false)
    private Long designId;

    public DesignPictureFile(String name, byte[] md5Hash, Long designId) {
        super(name, md5Hash);
        this.designId = designId;
    }

    public Long getDesignId() {
        return designId;
    }

    public void setDesignId(Long designId) {
        this.designId = designId;
    }
}
