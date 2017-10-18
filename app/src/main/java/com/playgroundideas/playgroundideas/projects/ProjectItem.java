package com.playgroundideas.playgroundideas.projects;


import android.os.Parcel;
import android.os.Parcelable;

import com.playgroundideas.playgroundideas.model.Project;

import java.util.Date;



public class ProjectItem implements Parcelable{

    private String mProjectTtile;
    private Date mStartDate;
    private Date mEndDate;
    private String mEmailAddress;
    private String mCountry;
    private String mCurrency;
    private String mProjectDescription;
    private String mImageUrl;
    private int mCurrentFund;
    private int mGoalFund;
    private int mDayleft;

    public ProjectItem(String projectTtile,Date startDate, Date endDate, String emailAddress,
                       String country, String currency, String projectDescription, String mImageUrl
                        ,int mCurrentFund,int mGoalFund,int mDayleft) {

        this.mProjectTtile = projectTtile;
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        this.mCountry = country;
        this.mCurrency = currency;
        this.mEmailAddress = emailAddress;
        this.mProjectDescription = projectDescription;
        this.mImageUrl = mImageUrl;
        this.mCurrentFund = mCurrentFund;
        this.mGoalFund = mGoalFund;
        this.mDayleft = mDayleft;
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

    public int getmCurrentFund() {
        return  mCurrentFund;
    }

    public int getmGoalFund() {
        return mGoalFund;
    }

    public int getmDayleft() {
        return mDayleft;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    //write projectItem into parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProjectTtile);
        dest.writeLong(mStartDate.getTime());
        dest.writeLong(mEndDate.getTime());
        dest.writeString(mEmailAddress);
        dest.writeString(mCountry);
        dest.writeString(mCurrency);
        dest.writeString(mProjectDescription);
        dest.writeString(mImageUrl);
        dest.writeInt(mCurrentFund);
        dest.writeInt(mGoalFund);
        dest.writeInt(mDayleft);

    }


    //read projectItem into parcel
    public static final Parcelable.Creator<ProjectItem> CREATOR = new Parcelable.Creator<ProjectItem>() {
        @Override
        public ProjectItem createFromParcel(Parcel source) {
            ProjectItem projectItem = new ProjectItem(source.readString(),new Date(source.readLong()),new Date(source.readLong()),
                    source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),
                    source.readInt(),source.readInt(),source.readInt());
            return projectItem;
        }

        @Override
        public ProjectItem[] newArray(int size) {
            return new ProjectItem[size];
        }
    };


}
