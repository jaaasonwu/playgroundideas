package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

/**
 * Created by Ferdinand on 13/09/2017.
 */

@Entity(indices = {@Index("userId"), @Index("designId")}, foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"), @ForeignKey(entity = Design.class, parentColumns = "id", childColumns = "designId")}, primaryKeys = {"userId", "designId"})
public class FavouritedDesign {

    @NonNull
    private Long userId;
    @NonNull
    private Long designId;

    public FavouritedDesign(Long userId, Long designId) {
        this.userId = userId;
        this.designId = designId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDesignId() {
        return designId;
    }

    public void setDesignId(Long designId) {
        this.designId = designId;
    }
}
