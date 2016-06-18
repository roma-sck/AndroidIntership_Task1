package com.example.sck.androidintership_task1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.activity.DetailActivity;
import com.example.sck.androidintership_task1.adapters.RealmRecyclerAdapter;
import com.example.sck.androidintership_task1.api.ApiConst;
import com.example.sck.androidintership_task1.utils.LoadMoreRecyclerScrollListener;
import com.example.sck.androidintership_task1.utils.RecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRecyclerList extends Fragment implements FragmentContract.View {

    @BindView(R.id.appeals_recycler_list) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    private static final String RECYCLER_KEY = "recycler_key";
    private int mLoadingTicketsAmount = 0;
    private FragmentPresenter mPresenter;

    public FragmentRecyclerList() {
        // required empty constructor
    }

    /**
     * Static factory method that takes an DataModel parameter,
     * initializes the fragment's arguments, and returns the
     * new fragment to the client.
     */
    public static FragmentRecyclerList getInstance(int state) {
        FragmentRecyclerList fragment = new FragmentRecyclerList();
        Bundle bundle = new Bundle();
        bundle.putInt(RECYCLER_KEY, state);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int getTabNum() {
        return getArguments().getInt(RECYCLER_KEY);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = new FragmentPresenter(getContext(), getTabNum());
            mPresenter.attachView(this);
        }
        mPresenter.initRealmDb();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        ButterKnife.bind(this, rootView);
        initSwipeToRefresh();
        mPresenter.loadApiDataFirstPage();
        mPresenter.loadDataFromDb();
        setUpRecyclerList();
        return rootView;
    }

    @Override
    public void setRealmAdapter(RealmRecyclerAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    private void setUpRecyclerList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new OnRecyclerItemClickListener()));
        mRecyclerView.addOnScrollListener(new LoadMoreRecyclerScrollListener(
                (LinearLayoutManager) mRecyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // load next amount of tickets
                mLoadingTicketsAmount += ApiConst.TICKETS_AMOUNT;
                mPresenter.loadApiDataNextPage(mLoadingTicketsAmount);
            }
        });
    }

    private void initSwipeToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.clearRealmDb();
                mLoadingTicketsAmount = 0;
                mPresenter.loadApiDataFirstPageAllTabs();
            }
        });
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideSwipeRefreshProgress(boolean progress) {
        mSwipeRefreshLayout.setRefreshing(progress);
    }

    private class OnRecyclerItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {
        @Override
        public void onItemClick(View childView, int position) {
            int modelId = mPresenter.getClickedItemId(position);
            Intent openDetail = new Intent(getActivity(), DetailActivity.class);
            openDetail.putExtra(getActivity().getString(R.string.intent_to_detail_extra_name), modelId);
            getActivity().startActivity(openDetail);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}