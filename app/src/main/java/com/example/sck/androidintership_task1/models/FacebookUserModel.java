package com.example.sck.androidintership_task1.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FacebookUserModel extends RealmObject {
    @PrimaryKey
    private String mFacebookID;
    private String mAccessToken;
    private String mName;

    public String getFacebookID() {
        return mFacebookID;
    }

    public void setFacebookID(String facebookID) {
        mFacebookID = facebookID;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
