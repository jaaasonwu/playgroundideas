package com.playgroundideas.playgroundideas.projects;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Date;

/**
 * Created by TongNiu on 8/9/17.
 */

public class ProjectItem {
    private String mProjectTtile;
    /*private Date mStartDate;
    private Date mEndDate;
    private String mEmailAddress;
    private String mCountry;
    private String mCurrency;
    private String mProjectDescription;*/

    private Bitmap mImage;

    /*public ProjectItem(String projectTtile,Date startDate, Date endDate, String emailAddress,
                       String country, String currency, String projectDescription, Bitmap image) {

        this.mProjectTtile = projectTtile;
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        this.mCountry = country;
        this.mCurrency = currency;
        this.mEmailAddress = emailAddress;
        this.mProjectDescription = projectDescription;
        this.mImage = image;
    }*/

    public ProjectItem(String projectTtile,Bitmap image) {
        this.mProjectTtile = projectTtile;
        this.mImage = image;
    }

    public String getProjectTtile() {
        return mProjectTtile;
    }

   /* public String getCountry() {
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
    }*/

    public Bitmap getImage() {
        return mImage;
    }
}
