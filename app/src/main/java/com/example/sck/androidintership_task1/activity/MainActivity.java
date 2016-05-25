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
import com.example.sck.androidintership_task1.fragments.FragmentListView;
import com.example.sck.androidintership_task1.fragments.FragmentRecyclerList;
import com.example.sck.androidintership_task1.model.ListItemModel;
import com.example.sck.androidintership_task1.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private DataModel mDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.all_appeals_text));

        setUpToolbar();
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do something
            }
        });
        setUpDrawer();
        fillModelWithData();
        setUpViewPager();
    }

    /**
     * set the toolbar view inside the activity layout.
     * sets the Toolbar to act as the ActionBar for this Activity window.
     */
    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar ();
        if(actionbar != null) {
            // custom home indicator icon
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Create a Data model
     * fill model with some data
     */
    public void fillModelWithData() {
        List<ListItemModel> data = new ArrayList<>();
        int groupIc = R.drawable.ic_trash;
        String likeCount = getString(R.string.list_item_data_like_count);
        String address = getString(R.string.list_item_data_address);
        String date = getString(R.string.list_item_data_date);
        String daysLeft = getString(R.string.list_item_data_days_left);
        String[] titles = getResources().getStringArray(R.array.list_item_data_appeals_title);
        for(int i = 0; i < 10; i++) {
            // add new instance of ListItemModel class with data to list
            data.add(new ListItemModel(groupIc, likeCount, titles[i], address, date, daysLeft));
        }
        mDataModel = new DataModel(data);
    }

    /**
     * setting ViewPager for each Tabs
     *
     */
    private void setUpViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // add Fragments to Tabs, transfer model with data
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(FragmentRecyclerList.getInstance(mDataModel), getString(R.string.appeals_tab_inWork));
        pagerAdapter.addFragment(FragmentRecyclerList.getInstance(mDataModel), getString(R.string.appeals_tab_done));
        pagerAdapter.addFragment(FragmentListView.getInstance(mDataModel), getString(R.string.appeals_tab_notDone));
        viewPager.setAdapter(pagerAdapter);
        // set Tabs inside ToolbarLayout
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
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
        } if (id == R.id.nav_sing_in) {
            this.startActivity(new Intent(this, FacebookLoginActivity.class));
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
