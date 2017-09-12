package com.playgroundideas.playgroundideas.designs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Peter Chen on 2017/9/5.
 */
class DesignItem implements Parcelable {
    private String description;
    private int image;

    protected  void setDescription(String description){
        this.description = description;
    }

    protected void setImage(int image){
        this.image = image;
    }
    protected String getDescription(){
        return new String(this.description);
    }

    protected int getImage(){
        return this.image;
    }

    DesignItem(String desc, int image){
        this.description = desc;
        this.image = image;
    }

    public  DesignItem(Parcel input){
        this.description = input.readString();
        this.image = input.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeInt(image);
    }

    public static final Creator<DesignItem> CREATOR
            = new Creator<DesignItem>() {
        public DesignItem createFromParcel(Parcel in) {
            return new DesignItem(in);
        }

        public DesignItem[] newArray(int size) {
            return new DesignItem[size];
        }
    };
}