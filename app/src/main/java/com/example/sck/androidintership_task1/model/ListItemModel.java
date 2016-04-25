package com.example.sck.androidintership_task1.model;

import java.io.Serializable;

public class ListItemModel implements Serializable { //[Comment] Unused setters. Use Parcelable instead of Serializable

    private int mImage;
    private String mLikeCount;
    private String mTitle;
    private String mAddress;
    private String mDate;
    private String mDaysLeft;

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

    public void setImage(int image) {
        mImage = image;
    }


    public String getLikeCount() {
        return mLikeCount;
    }

    public void setLikeCount(String likeCount) {
        mLikeCount = likeCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDaysLeft() {
        return mDaysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        mDaysLeft = daysLeft;
    }

}
