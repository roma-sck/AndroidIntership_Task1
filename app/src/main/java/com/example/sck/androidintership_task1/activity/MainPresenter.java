package com.example.sck.androidintership_task1.activity;

import android.content.Context;
import android.content.Intent;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.adapters.MainPagerAdapter;
import com.example.sck.androidintership_task1.fragments.FragmentRecyclerList;

public class MainPresenter implements MainContract.Presenter {
    public static final int TAB_ONE = 1;
    public static final int TAB_TWO = 2;
    public static final int TAB_THREE = 3;
    private Context mContext;
    private MainContract.View mView;

    public MainPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void attachView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void preparePagerAdapter() {
        // add Fragments to Tabs
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(mView.getSupportFragmentManager());
        pagerAdapter.addFragment(FragmentRecyclerList.getInstance(TAB_ONE),
                mContext.getString(R.string.appeals_tab_inWork));
        pagerAdapter.addFragment(FragmentRecyclerList.getInstance(TAB_TWO),
                mContext.getString(R.string.appeals_tab_done));
        pagerAdapter.addFragment(FragmentRecyclerList.getInstance(TAB_THREE),
                mContext.getString(R.string.appeals_tab_notDone));
        mView.setUpViewPager(pagerAdapter);
    }

    @Override
    public void onShowMapClick() {
        mContext.startActivity(new Intent(mContext, MapActivity.class));
    }

    @Override
    public void onFacebookLogInClick() {
        mContext.startActivity(new Intent(mContext, FacebookProfileActivity.class));
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
