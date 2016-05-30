package com.example.sck.androidintership_task1.activity;

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

import com.example.sck.androidintership_task1.adapters.ImagesRecyclerAdapter;
import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.models.File;
import com.example.sck.androidintership_task1.models.IssueDataModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.content) ViewGroup mAllContent;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    private static final String ID_FIELD = "id";
    private IssueDataModel mItemModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        // get itemId sent from Fragment
        Bundle bundle = this.getIntent().getExtras();
        int itemId = bundle.getInt(getString(R.string.intent_to_detail_extra_name));

        mItemModel = getModelById(itemId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(mItemModel.getTicketId());
        initToolbar();
        initRecyclerView();
        setOnClickAllViews(mAllContent);
    }

    private IssueDataModel getModelById(int id) {
        Realm realm = Realm.getDefaultInstance();
        IssueDataModel results = realm.where(IssueDataModel.class)
                .equalTo(ID_FIELD, id)
                .findFirst();
        realm.close();
        return results;
    }

    /**
     * find the toolbar view inside the activity layout.
     * sets the Toolbar to act as the ActionBar for this Activity window.
     * make sure the toolbar exists in the activity and is not null, add backbutton onClick
     */
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        assert mToolbar != null;
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * find RecyclerView, set display downloaded images in a horizontal scrollable list
     */
    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        List<File> pictures = null;
        if (mItemModel.getFiles() != null) {
            pictures = mItemModel.getFiles();
        }
        ImagesRecyclerAdapter adapter = new ImagesRecyclerAdapter(this, pictures);
        mRecyclerView.setAdapter(adapter);
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

    /**
     * shows simple name of clicked view
     *
     * @param view view
     */
    @Override
    public void onClick(View view) {
        Toast.makeText(this, view.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }
}
