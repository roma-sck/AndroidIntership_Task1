package com.example.sck.androidintership_task1.activity;

import android.app.Activity;
import android.content.Intent;

import com.facebook.Profile;

public interface FacebookProfileContract {
    interface Presenter {
        void logInToProfile();
        void onActivityResult(int requestCode, int resultCode, Intent data);
        void saveProfileToDb(Profile profile);
        void logOutProfile();
        void onStopTracking();
    }

    interface View {
        void updateViews(Profile profile);
        Activity getActivity();
    }
}
