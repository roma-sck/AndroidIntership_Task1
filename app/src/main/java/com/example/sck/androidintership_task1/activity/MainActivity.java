package com.example.sck.androidintership_task1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.adapters.MainPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.action_button) FloatingActionButton mActionButton;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView mNavigationView;
    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout mTabs;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.all_appeals_text));
        ButterKnife.bind(this);
        if (mPresenter == null) {
            mPresenter = new MainPresenter(this);
            mPresenter.attachView(this);
        }
        setUpToolbar();
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do something
            }
        });
        mNavigationView.setNavigationItemSelectedListener(this);
        mPresenter.preparePagerAdapter();
    }

    /**
     * set the toolbar view inside the activity layout.
     * sets the Toolbar to act as the ActionBar for this Activity window.
     */
    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null) {
            // custom home indicator icon
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * setting ViewPager for each Tabs
     *
     */
    @Override
    public void setUpViewPager(MainPagerAdapter pagerAdapter) {
        mViewPager.setAdapter(pagerAdapter);
        // set 2 tabs that should be retained to either side of the current page
        mViewPager.setOffscreenPageLimit(2);
        // set Tabs inside ToolbarLayout
        mTabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();
        if (id == R.id.action_filter_menu) {
            // do something
            return true;
        } else if (id == android.R.id.home) {
            mDrawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_all_appeals) {
            // do something
        } if (id == R.id.nav_appeals_on_map) {
            // do something
            startActivity(new Intent(this, MapActivity.class));
        } if (id == R.id.nav_sing_in) {
            mPresenter.onFacebookLogInClick();
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
