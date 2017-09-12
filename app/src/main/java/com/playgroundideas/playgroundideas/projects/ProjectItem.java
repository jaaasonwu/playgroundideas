package com.playgroundideas.playgroundideas.projects;


import java.util.Date;



public class ProjectItem {

    private String mProjectTtile;
    private Date mStartDate;
    private Date mEndDate;
    private String mEmailAddress;
    private String mCountry;
    private String mCurrency;
    private String mProjectDescription;
    private String mImageUrl;

    public ProjectItem(String projectTtile,Date startDate, Date endDate, String emailAddress,
                       String country, String currency, String projectDescription, String mImageUrl) {

        this.mProjectTtile = projectTtile;
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        this.mCountry = country;
        this.mCurrency = currency;
        this.mEmailAddress = emailAddress;
        this.mProjectDescription = projectDescription;
        this.mImageUrl = mImageUrl;
    }


    public String getProjectTtile() {
        return mProjectTtile;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public String getProjectDescription() {
        return mProjectDescription;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

   public String getImageUrl() {
        return mImageUrl;
    }
}
