package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.playgroundideas.playgroundideas.datasource.local.Converters;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity(indices = {@Index("category"), @Index("creatorId")},
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "creatorId")})
public class Design extends VersionedEntity {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private Long id;
    private String name;
    private Long creatorId;
    @TypeConverters(value = Converters.class)
    private DesignCategory category;
    private String description;
    private boolean published;
    private boolean pickedByStaff;
    private String safetyConsiderations;
    private String buildingSteps;
    private String buildingMaterials;
    private String buildingTools;
    private String pictureUrl;

    public Design(long version, Long id, String name, Long creatorId, DesignCategory category, String description, String buildingMaterials, boolean published, boolean pickedByStaff, String safetyConsiderations, String buildingSteps, String buildingTools, String pictureUrl) {
        super(version);
        this.id = id;
        this.name = name;
        this.creatorId = creatorId;
        this.category = category;
        this.description = description;
        this.buildingMaterials = buildingMaterials;
        this.published = published;
        this.pickedByStaff = pickedByStaff;
        this.safetyConsiderations = safetyConsiderations;
        this.buildingSteps = buildingSteps;
        this.buildingTools = buildingTools;
        this.pictureUrl = pictureUrl;
    }


    public String getPictureUrl() {return pictureUrl;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isPickedByStaff() {
        return pickedByStaff;
    }

    public void setPickedByStaff(boolean pickedByStaff) {
        this.pickedByStaff = pickedByStaff;
    }

    public String getSafetyConsiderations() {
        return safetyConsiderations;
    }

    public void setSafetyConsiderations(String safetyConsiderations) {
        this.safetyConsiderations = safetyConsiderations;
    }

    public String getBuildingSteps() {
        return buildingSteps;
    }

    public void setBuildingSteps(String buildingSteps) {
        this.buildingSteps = buildingSteps;
    }

    public String getBuildingMaterials() {
        return buildingMaterials;
    }

    public void setBuildingMaterials(String buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
    }

    public String getBuildingTools() {
        return buildingTools;
    }

    public void setBuildingTools(String buildingTools) {
        this.buildingTools = buildingTools;
    }
}
