package com.playgroundideas.playgroundideas.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.playgroundideas.playgroundideas.datasource.local.Converters;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity(indices = {@Index("category"), @Index("authorId")},
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "authorId")})
public class Design {

    @PrimaryKey(autoGenerate = false)
    private Long id;
    private Long authorId;
    @TypeConverters(value = Converters.class)
    private DesignCategory category;
    private String description;
    private String materials;
    private String tools;
    private String pictureFileName;

    public Design(Long id, Long authorId, DesignCategory category, String description, String materials, String tools, String pictureFileName) {
        this.id = id;
        this.authorId = authorId;
        this.category = category;
        this.description = description;
        this.materials = materials;
        this.tools = tools;
        this.pictureFileName = pictureFileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

    public String getPictureFileName() {
        return pictureFileName;
    }

    public void setPictureFileName(String pictureFileName) {
        this.pictureFileName = pictureFileName;
    }
}
