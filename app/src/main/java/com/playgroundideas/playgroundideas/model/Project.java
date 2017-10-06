package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.playgroundideas.playgroundideas.datasource.local.Converters;

import java.net.URL;
import java.util.Date;

import javax.money.MonetaryAmount;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity(indices = {@Index("creatorId")}, foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "creatorId")})
public class Project extends  VersionedEntity{

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private Long id;
    private String name;
    @TypeConverters(value = Converters.class)
    private ProjectPostStatus projectPostStatus;
    private String location;
    private boolean requiresFunding;
    private boolean seekingVolunteers;
    private String description;
    private Long creatorId;
    private int numberOfDonations;
    @TypeConverters(Converters.class)
    private Date startDate;
    @TypeConverters(Converters.class)
    private Date endDate;
    private boolean manuallyAddingFundsAllowed;
    @TypeConverters(Converters.class)
    private URL facebook_campaign_link;
    @TypeConverters(Converters.class)
    private URL twitter__campaign_link;
    @TypeConverters(Converters.class)
    private URL google_campaing_link;
    @TypeConverters(Converters.class)
    private URL pinterest_campaign_link;
    @TypeConverters(Converters.class)
    private URL linkedin_campaign_link;
    @TypeConverters(Converters.class)
    private URL youtube_campaign_link;
    @TypeConverters(Converters.class)
    private MonetaryAmount fundingSum;
    @TypeConverters(Converters.class)
    private MonetaryAmount fundingGoal;

    public Project(long version, Long id, String location, boolean requiresFunding, boolean seekingVolunteers, String description, Long creatorId, int numberOfDonations, MonetaryAmount fundingSum, MonetaryAmount fundingGoal, boolean manuallyAddingFundsAllowed, URL facebook_campaign_link, URL pinterest_campaign_link, URL google_campaing_link, URL linkedin_campaign_link, URL youtube_campaign_link, URL twitter__campaign_link, Date startDate, Date endDate) {
        super(version);
        this.id = id;
        this.location = location;
        this.requiresFunding = requiresFunding;
        this.seekingVolunteers = seekingVolunteers;
        this.description = description;
        this.creatorId = creatorId;
        this.numberOfDonations = numberOfDonations;
        this.fundingSum = fundingSum;
        this.fundingGoal = fundingGoal;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manuallyAddingFundsAllowed = manuallyAddingFundsAllowed;
        this.facebook_campaign_link = facebook_campaign_link;
        this.google_campaing_link = google_campaing_link;
        this.twitter__campaign_link = twitter__campaign_link;
        this.linkedin_campaign_link = linkedin_campaign_link;
        this.youtube_campaign_link = youtube_campaign_link;
        this.pinterest_campaign_link = pinterest_campaign_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectPostStatus getProjectPostStatus() {
        return projectPostStatus;
    }

    public void setProjectPostStatus(ProjectPostStatus projectPostStatus) {
        this.projectPostStatus = projectPostStatus;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isManuallyAddingFundsAllowed() {
        return manuallyAddingFundsAllowed;
    }

    public void setManuallyAddingFundsAllowed(boolean manuallyAddingFundsAllowed) {
        this.manuallyAddingFundsAllowed = manuallyAddingFundsAllowed;
    }

    public URL getFacebook_campaign_link() {
        return facebook_campaign_link;
    }

    public void setFacebook_campaign_link(URL facebook_campaign_link) {
        this.facebook_campaign_link = facebook_campaign_link;
    }

    public URL getTwitter__campaign_link() {
        return twitter__campaign_link;
    }

    public void setTwitter__campaign_link(URL twitter__campaign_link) {
        this.twitter__campaign_link = twitter__campaign_link;
    }

    public URL getGoogle_campaing_link() {
        return google_campaing_link;
    }

    public void setGoogle_campaing_link(URL google_campaing_link) {
        this.google_campaing_link = google_campaing_link;
    }

    public URL getPinterest_campaign_link() {
        return pinterest_campaign_link;
    }

    public void setPinterest_campaign_link(URL pinterest_campaign_link) {
        this.pinterest_campaign_link = pinterest_campaign_link;
    }

    public URL getLinkedin_campaign_link() {
        return linkedin_campaign_link;
    }

    public void setLinkedin_campaign_link(URL linkedin_campaign_link) {
        this.linkedin_campaign_link = linkedin_campaign_link;
    }

    public URL getYoutube_campaign_link() {
        return youtube_campaign_link;
    }

    public void setYoutube_campaign_link(URL youtube_campaign_link) {
        this.youtube_campaign_link = youtube_campaign_link;
    }
}
