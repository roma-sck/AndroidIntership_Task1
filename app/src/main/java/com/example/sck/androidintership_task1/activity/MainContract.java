package com.example.sck.androidintership_task1.activity;

import android.support.v4.app.FragmentManager;

import com.example.sck.androidintership_task1.adapters.MainPagerAdapter;

public interface MainContract {
    interface Presenter {
        void attachView(View view);
        void preparePagerAdapter();
        void onShowMapClick();
        void onFacebookLogInClick();
        void detachView();
    }

    interface View {
        FragmentManager getSupportFragmentManager();
        void setUpViewPager(MainPagerAdapter adapter);
    }
}
