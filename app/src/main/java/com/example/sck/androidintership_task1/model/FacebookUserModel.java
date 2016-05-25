package com.example.sck.androidintership_task1.model;

public class FacebookUserModel {
    private String mName;
    private String mEmail;
    private String mFacebookID;

    public FacebookUserModel(String name, String email, String facebookID) {
        mName = name;
        mEmail = email;
        mFacebookID = facebookID;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getFacebookID() {
        return mFacebookID;
    }
}
