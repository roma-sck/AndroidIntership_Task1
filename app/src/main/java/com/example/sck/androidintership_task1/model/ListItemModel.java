package com.example.sck.androidintership_task1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItemModel implements Parcelable {
    private int mImage;
    private String mLikeCount;
    private String mTitle;
    private String mAddress;
    private String mDate;
    private String mDaysLeft;

    /**
     * A constructor that initializes the ListItemModel object
     **/
    public ListItemModel(int image, String likeCount, String title, String address, String date, String daysLeft) {
        mImage = image;
        mLikeCount = likeCount;
        mTitle = title;
        mAddress = address;
        mDate = date;
        mDaysLeft = daysLeft;
    }

    public int getImage() {
        return mImage;
    }

    public String getLikeCount() {
        return mLikeCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getDate() {
        return mDate;
    }

    public String getDaysLeft() {
        return mDaysLeft;
    }

    /**
     * Retrieving ListItemModel data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    protected ListItemModel(Parcel in) {
        mImage = in.readInt();
        mLikeCount = in.readString();
        mTitle = in.readString();
        mAddress = in.readString();
        mDate = in.readString();
        mDaysLeft = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the ListItemModel data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mImage);
        dest.writeString(mLikeCount);
        dest.writeString(mTitle);
        dest.writeString(mAddress);
        dest.writeString(mDate);
        dest.writeString(mDaysLeft);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListItemModel> CREATOR = new Parcelable.Creator<ListItemModel>() {
        @Override
        public ListItemModel createFromParcel(Parcel in) {
            return new ListItemModel(in);
        }

        @Override
        public ListItemModel[] newArray(int size) {
            return new ListItemModel[size];
        }
    };
}
