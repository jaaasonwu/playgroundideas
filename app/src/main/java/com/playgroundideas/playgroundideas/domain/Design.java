package com.playgroundideas.playgroundideas.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;
import java.util.Map;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public class Design {

    @PrimaryKey
    private long id;
    private User author;
    private DesignCategory category;
    private String description;
    private Map<String, Integer> materials;
    private List<String> tools;
    private String pictureFileName;

    public Design(Long id, User author, DesignCategory category, String description, Map<String, Integer> materials, List<String> tools, String pictureFileName) {
        this.id = id;
        this.author = author;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public Map<String, Integer> getMaterials() {
        return materials;
    }

    public void setMaterials(Map<String, Integer> materials) {
        this.materials = materials;
    }

    public List<String> getTools() {
        return tools;
    }

    public void setTools(List<String> tools) {
        this.tools = tools;
    }

    public String getPictureFileName() {
        return pictureFileName;
    }

    public void setPictureFileName(String pictureFileName) {
        this.pictureFileName = pictureFileName;
    }
}
