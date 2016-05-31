package com.example.sck.androidintership_task1.activity;

import android.support.v4.app.FragmentManager;

import com.example.sck.androidintership_task1.adapters.MainPagerAdapter;

public class MainContract {
    interface Presenter {
        void preparePagerAdapter();
        void onFacebookLogInClick();
    }

    interface View {
        FragmentManager getSupportFragmentManager();
        void setUpViewPager(MainPagerAdapter adapter);
    }
}
