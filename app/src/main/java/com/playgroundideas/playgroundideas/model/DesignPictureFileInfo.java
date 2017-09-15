package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Ferdinand on 12/09/2017.
 */

@Entity(foreignKeys = {@ForeignKey(entity = Design.class, parentColumns = "id", childColumns = "designId") })
public class DesignPictureFileInfo extends FileInfo {

    private Long designId;

    public DesignPictureFileInfo(byte[] md5Hash, Long designId) {
        super(designId.toString(), md5Hash);
        this.designId = designId;
    }

    public Long getDesignId() {
        return designId;
    }

    public void setDesignId(Long designId) {
        this.designId = designId;
    }
}
