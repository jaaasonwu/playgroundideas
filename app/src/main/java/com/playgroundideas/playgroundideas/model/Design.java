package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.playgroundideas.playgroundideas.datasource.local.Converters;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity(indices = {@Index("category"), @Index("_authorId")},
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "_authorId")})
public class Design {

    @PrimaryKey(autoGenerate = false)
    private Long id;
    @Ignore
    private User author;
    //used by Room to persist relationship; do NOT access it!
    public Long _authorId;
    @TypeConverters(value = Converters.class)
    private DesignCategory category;
    private String description;
    private String materials;
    private String tools;
    @Embedded(prefix = "picture_")
    private DesignPictureFile picture;

    @Ignore
    public Design(Long id, User author, Long _authorId, DesignCategory category, String description, String materials, String tools, DesignPictureFile picture) {
        this.id = id;
        this.author = author;
        // parameter authorId is ignored
        this._authorId = author.getId();
        this.category = category;
        this.description = description;
        this.materials = materials;
        this.tools = tools;
        this.picture = picture;
    }

    // used by Room; use the other constructor
    public Design(Long id, Long _authorId, DesignCategory category, String description, String materials, String tools, DesignPictureFile picture) {
        this.id = id;
        this._authorId = _authorId;
        this.category = category;
        this.description = description;
        this.materials = materials;
        this.tools = tools;
        this.picture = picture;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
        this._authorId = author.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DesignCategory getCategory() {
        return category;
    }

    public void setCategory(DesignCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public DesignPictureFile getPicture() {
        return picture;
    }

    public void setPicture(DesignPictureFile picture) {
        this.picture = picture;
    }
}
