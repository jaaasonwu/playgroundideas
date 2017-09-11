package com.playgroundideas.playgroundideas.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.icu.util.CurrencyAmount;
import android.location.Address;

import java.util.List;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public class Project {

    @PrimaryKey
    private Long id;
    private Address location;
    private boolean requiresFunding;
    private boolean seekingVolunteers;
    private List<String> picturesFileNames;
    private String description;
    private User creator;
    private int numberOfDonations;
    private int daysLeftUntilFundingEnd;
    private CurrencyAmount fundingSum;
    private CurrencyAmount fundingGoal;

    public Project(Long id, Address location, boolean requiresFunding, boolean seekingVolunteers, List<String> picturesFileNames, String description, User creator, int numberOfDonations, int daysLeftUntilFundingEnd, CurrencyAmount fundingSum, CurrencyAmount fundingGoal) {

        this.id = id;
        this.location = location;
        this.requiresFunding = requiresFunding;
        this.seekingVolunteers = seekingVolunteers;
        this.picturesFileNames = picturesFileNames;
        this.description = description;
        this.creator = creator;
        this.numberOfDonations = numberOfDonations;
        this.daysLeftUntilFundingEnd = daysLeftUntilFundingEnd;
        this.fundingSum = fundingSum;
        this.fundingGoal = fundingGoal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
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

    public List<String> getPicturesFileNames() {
        return picturesFileNames;
    }

    public void setPicturesFileNames(List<String> picturesFileNames) {
        this.picturesFileNames = picturesFileNames;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public CurrencyAmount getFundingSum() {
        return fundingSum;
    }

    public void setFundingSum(CurrencyAmount fundingSum) {
        this.fundingSum = fundingSum;
    }

    public CurrencyAmount getFundingGoal() {
        return fundingGoal;
    }

    public void setFundingGoal(CurrencyAmount fundingGoal) {
        this.fundingGoal = fundingGoal;
    }
}
