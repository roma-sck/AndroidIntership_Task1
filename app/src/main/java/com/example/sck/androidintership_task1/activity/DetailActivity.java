package com.example.sck.androidintership_task1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sck.androidintership_task1.adapters.ImagesRecyclerAdapter;
import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.models.File;
import com.example.sck.androidintership_task1.models.IssueDataModel;
import com.example.sck.androidintership_task1.utils.DateConverter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailContract.View, View.OnClickListener {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.content) ViewGroup mAllContent;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.item_title) TextView mTitle;
    @BindView(R.id.status) TextView mStatus;
    @BindView(R.id.created_date) TextView mCreatedDate;
    @BindView(R.id.registered_date) TextView mRegisteredDate;
    @BindView(R.id.resolve_to_date) TextView mResolveToDate;
    @BindView(R.id.responsible_name) TextView mResponcibleName;
    @BindView(R.id.body) TextView mBodyText;
    private List<File> mPictures;
    private DetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // get itemId sent from Fragment
        Bundle bundle = this.getIntent().getExtras();
        int itemId = bundle.getInt(getString(R.string.intent_to_detail_extra_name));
        if (mPresenter == null) {
            mPresenter = new DetailPresenter();
            mPresenter.attachView(this);
        }
        mPresenter.loadModelById(itemId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initToolbar();
        initRecyclerView();
        setOnClickAllViews(mAllContent);
    }

    @Override
    public void updateViews(IssueDataModel itemModel) {
        setTitle(itemModel.getTicketId());
        mTitle.setText(itemModel.getTitle());
        mStatus.setText(itemModel.getState().getName());
        mCreatedDate.setText(DateConverter.convertDate(itemModel.getCreatedDate()));
        mRegisteredDate.setText(DateConverter.convertDate(itemModel.getStartDate()));
        if (itemModel.getDeadline() != 0) {
            mResolveToDate.setText(DateConverter.convertDate(itemModel.getDeadline()));
        } else {
            mResolveToDate.setText("");
        }
        if (itemModel.getPerformers().size() != 0) {
            mResponcibleName.setText(itemModel.getPerformers().get(0).getOrganization());
        } else {
            mResponcibleName.setText("");
        }
        mBodyText.setText(itemModel.getBody());
        if (itemModel.getFiles() != null) {
            mPictures = itemModel.getFiles();
        }
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
        ImagesRecyclerAdapter adapter = new ImagesRecyclerAdapter(this, mPictures);
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

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
