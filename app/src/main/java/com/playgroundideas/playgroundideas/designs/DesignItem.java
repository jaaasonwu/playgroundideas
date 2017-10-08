package com.playgroundideas.playgroundideas.designs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Peter Chen on 2017/9/5.
 */
class DesignItem implements Parcelable {
    private String name;
    private String imageUrl;
    private  String catergory;

    protected  void setName(String name){
        this.name = name;
    }

    protected void setImageURl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    protected void setImageCatergory(String catergory) {this.catergory = catergory;}

    protected String getName(){
        return new String(this.name);
    }

    protected String getImageUrl() {return this.imageUrl;}

    protected String getCatergory() {return this.catergory;}

    DesignItem(String name, String imageUrl, String catergory){
        this.name = name;
        this.imageUrl = imageUrl;
        this.catergory = catergory;

    }

    DesignItem(DesignItem designItem){
        this.name = designItem.getName();
        this.imageUrl = designItem.getImageUrl();
        this.catergory = designItem.getCatergory();
    }

    public  DesignItem(Parcel input){
        this.name = input.readString();
        this.imageUrl = input.readString();
        this.catergory = input.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(imageUrl);
        parcel.writeString(catergory);
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
