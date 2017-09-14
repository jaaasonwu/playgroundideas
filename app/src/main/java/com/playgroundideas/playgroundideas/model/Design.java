package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.playgroundideas.playgroundideas.datasource.local.Converters;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity(indices = {@Index("category"), @Index("creatorId")},
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "creatorId")})
public class Design {

    @PrimaryKey(autoGenerate = false)
    private Long id;
    private String name;
    private Long creatorId;
    @TypeConverters(value = Converters.class)
    private DesignCategory category;
    private String description;
    private String materials;
    private String tools;
    @Embedded(prefix = "picture_")
    private DesignPictureFileInfo picture;

    public Design(Long id, String name, Long creatorId, DesignCategory category, String description, String materials, String tools, DesignPictureFileInfo picture) {
        this.id = id;
        this.name = name;
        this.creatorId = creatorId;
        this.category = category;
        this.description = description;
        this.materials = materials;
        this.tools = tools;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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

    public DesignPictureFileInfo getPicture() {
        return picture;
    }

    public void setPicture(DesignPictureFileInfo picture) {
        this.picture = picture;
    }
}
