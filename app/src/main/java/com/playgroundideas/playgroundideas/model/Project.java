package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.playgroundideas.playgroundideas.datasource.local.Converters;

import javax.money.MonetaryAmount;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity(indices = {@Index("creatorId")}, foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "creatorId")})
public class Project {

    @PrimaryKey(autoGenerate = false)
    private Long id;
    private String location;
    private boolean requiresFunding;
    private boolean seekingVolunteers;
    private String description;
    private Long creatorId;
    private int numberOfDonations;
    private int daysLeftUntilFundingEnd;
    @TypeConverters(Converters.class)
    private MonetaryAmount fundingSum;
    @TypeConverters(Converters.class)
    private MonetaryAmount fundingGoal;

    public Project(Long id, String location, boolean requiresFunding, boolean seekingVolunteers, String description, Long creatorId, int numberOfDonations, int daysLeftUntilFundingEnd, MonetaryAmount fundingSum, MonetaryAmount fundingGoal) {
        this.id = id;
        this.location = location;
        this.requiresFunding = requiresFunding;
        this.seekingVolunteers = seekingVolunteers;
        this.description = description;
        this.creatorId = creatorId;
        this.numberOfDonations = numberOfDonations;
        this.daysLeftUntilFundingEnd = daysLeftUntilFundingEnd;
        this.fundingSum = fundingSum;
        this.fundingGoal = fundingGoal;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isRequiresFunding() {
        return requiresFunding;
    }

    public void setRequiresFunding(boolean requiresFunding) {
        this.requiresFunding = requiresFunding;
    }

    public boolean isSeekingVolunteers() {
        return seekingVolunteers;
    }

    public void setSeekingVolunteers(boolean seekingVolunteers) {
        this.seekingVolunteers = seekingVolunteers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfDonations() {
        return numberOfDonations;
    }

    public void setNumberOfDonations(int numberOfDonations) {
        this.numberOfDonations = numberOfDonations;
    }

    public int getDaysLeftUntilFundingEnd() {
        return daysLeftUntilFundingEnd;
    }

    public void setDaysLeftUntilFundingEnd(int daysLeftUntilFundingEnd) {
        this.daysLeftUntilFundingEnd = daysLeftUntilFundingEnd;
    }

    public MonetaryAmount getFundingSum() {
        return fundingSum;
    }

    public void setFundingSum(MonetaryAmount fundingSum) {
        this.fundingSum = fundingSum;
    }

    public MonetaryAmount getFundingGoal() {
        return fundingGoal;
    }

    public void setFundingGoal(MonetaryAmount fundingGoal) {
        this.fundingGoal = fundingGoal;
    }
}
