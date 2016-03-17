package com.example.sck.androidintership_task1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.toolbar_title));

        initToolbar();

        initRecyclerView();

        ViewGroup allContent = (ScrollView) findViewById(R.id.content);
        setOnClickAllViews(allContent);
    }

    private void initToolbar() {
        // find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        // make sure the toolbar exists in the activity and is not null
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);

        // to display downloaded images in a horizontal scrollable list
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, getResources().getStringArray(R.array.image_urls));
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickAllViews(ViewGroup viewGroup) {

        int childCount = viewGroup.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);

            if (view instanceof ViewGroup) {
                // if ViewGroup - finding inside
                setOnClickAllViews((ViewGroup) view);
            } else if (view instanceof TextView || view instanceof ImageView) {
                // if view - set onClick
                view.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        //view.getClass().getSimpleName() + "-" + getResources().getResourceName(view.getId()
        // shows simple name of clicked view
        Toast.makeText(this, view.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }
}
