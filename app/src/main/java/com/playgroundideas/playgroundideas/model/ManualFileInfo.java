package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;

/**
 * Created by Ferdinand on 12/09/2017.
 */

@Entity(foreignKeys = {@ForeignKey(entity = Manual.class, parentColumns = "id", childColumns = "manualId")})
public class ManualFileInfo extends FileInfo {

    private Long manualId;

    @Ignore
    public ManualFileInfo(Long manualId) {
        super(manualId.toString());
        this.manualId = manualId;
    }

    public ManualFileInfo(byte[] md5Hash, Long manualId) {
        super(manualId.toString(), md5Hash);
        this.manualId = manualId;
    }

    public Long getManualId() {
        return manualId;
    }

    public void setManualId(Long manualId) {
        this.manualId = manualId;
    }
}
