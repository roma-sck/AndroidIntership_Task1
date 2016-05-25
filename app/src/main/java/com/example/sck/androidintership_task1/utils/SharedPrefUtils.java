package com.example.sck.androidintership_task1.utils;

import android.content.Context;

import com.example.sck.androidintership_task1.model.FacebookUserModel;

/**
 * save facebook account data to shared preferences
 *
 */
public class SharedPrefUtils {
    public static final String PREFS_NAME = "user_prefs";
    public static final String CURRENT_USER_PREFS = "current_user_value";
    public static final int PREFS_MODE = 0;

    public static void setCurrentUser(FacebookUserModel currentUser, Context ctx){
        SharedComplexPrefs complexPrefs = SharedComplexPrefs.getComplexPrefs(ctx, PREFS_NAME, PREFS_MODE);
        complexPrefs.putObject(CURRENT_USER_PREFS, currentUser);
        complexPrefs.commit();
    }

    public static FacebookUserModel getCurrentUser(Context ctx){
        SharedComplexPrefs complexPrefs = SharedComplexPrefs.getComplexPrefs(ctx, PREFS_NAME, PREFS_MODE);
        return complexPrefs.getObject(CURRENT_USER_PREFS, FacebookUserModel.class);
    }

    public static void clearCurrentUser( Context ctx){
        SharedComplexPrefs complexPrefs = SharedComplexPrefs.getComplexPrefs(ctx, PREFS_NAME, PREFS_MODE);
        complexPrefs.clearObject();
        complexPrefs.commit();
    }
}
