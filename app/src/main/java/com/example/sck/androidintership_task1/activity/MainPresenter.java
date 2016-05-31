package com.example.sck.androidintership_task1.activity;

import android.content.Context;
import android.content.Intent;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.adapters.MainPagerAdapter;
import com.example.sck.androidintership_task1.fragments.FragmentRecyclerList;

public class MainPresenter implements MainContract.Presenter {
    private Context mContext;
    private MainContract.View mView;

    public MainPresenter(Context context, MainContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void preparePagerAdapter() {
        // add Fragments to Tabs
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(mView.getSupportFragmentManager());
        pagerAdapter.addFragment(FragmentRecyclerList.getInstance(FragmentRecyclerList.TAB_ONE),
                mContext.getString(R.string.appeals_tab_inWork));
        pagerAdapter.addFragment(FragmentRecyclerList.getInstance(FragmentRecyclerList.TAB_TWO),
                mContext.getString(R.string.appeals_tab_done));
        pagerAdapter.addFragment(FragmentRecyclerList.getInstance(FragmentRecyclerList.TAB_THREE),
                mContext.getString(R.string.appeals_tab_notDone));
        mView.setUpViewPager(pagerAdapter);
    }

    @Override
    public void onFacebookLogInClick() {
        mContext.startActivity(new Intent(mContext, FacebookProfileActivity.class));
    }
}
