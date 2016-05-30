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
import com.example.sck.androidintership_task1.api.ApiController;
import com.example.sck.androidintership_task1.api.ApiService;
import com.example.sck.androidintership_task1.models.IssueDataModel;
import com.example.sck.androidintership_task1.utils.LoadMoreRecyclerScrollListener;
import com.example.sck.androidintership_task1.utils.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FragmentRecyclerList extends Fragment {

    @BindView(R.id.appeals_recycler_list) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    public static final int TAB_ONE = 1;
    public static final int TAB_TWO = 2;
    public static final int TAB_THREE = 3;
    private static final String RECYCLER_KEY = "recycler_key";
    private ApiService mApiService;
    private RealmConfiguration mRealmConfig;
    private RealmResults<IssueDataModel> mResults;
    private int mLoadingTicketsAmount = 0;

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

    public int getTabNum() {
        return getArguments().getInt(RECYCLER_KEY);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRealmDb();
        mApiService = ApiController.getApiService();
        loadApiDataFirstPage();
    }

    private void loadApiDataFirstPage() {
        loadApiData(ApiConst.STATE_IN_PROGRESS, ApiConst.TICKETS_AMOUNT, ApiConst.TICKETS_WITHOUT_OFFSET);
        loadApiData(ApiConst.STATE_IN_DONE, ApiConst.TICKETS_AMOUNT, ApiConst.TICKETS_WITHOUT_OFFSET);
        loadApiData(ApiConst.STATE_IN_PENDING, ApiConst.TICKETS_AMOUNT, ApiConst.TICKETS_WITHOUT_OFFSET);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        ButterKnife.bind(this, rootView);
        setUpRecyclerList();
        loadDataFromDb();
        initSwipeToRefresh();
        return rootView;
    }

    private void initRealmDb() {
        if (mRealmConfig == null) {
            mRealmConfig = new RealmConfiguration
                    .Builder(this.getActivity().getApplication().getApplicationContext())
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(mRealmConfig);
        }
        Realm.getDefaultInstance();
    }

    private void loadApiData(String state, int amount, int offset) {
        Observable<List<IssueDataModel>> observable;
        if(offset == ApiConst.TICKETS_WITHOUT_OFFSET) {
            observable = mApiService.loadData(state, amount);
        } else {
            observable = mApiService.loadData(state, amount, offset);
        }
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<IssueDataModel>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(List<IssueDataModel> issues) {
                        Realm realm = Realm.getDefaultInstance();
                        // add content to the Relam DB
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(issues);
                        realm.commitTransaction();
                        realm.close();
                    }
                });
    }

    private void loadDataFromDb() {
        String stateValue = getStateValue(getTabNum());
        Realm realm = Realm.getDefaultInstance();
        if (stateValue.equals(ApiConst.STATE_IN_PENDING_VALUES)) {
            mResults = realm.where(IssueDataModel.class)
                    .contains(ApiConst.STATE_FIELD_NAME, ApiConst.STATE_IN_PENDING_VALUE_1)
                    .or()
                    .contains(ApiConst.STATE_FIELD_NAME, ApiConst.STATE_IN_PENDING_VALUE_2)
                    .findAllAsync();
        } else {
            mResults = realm.where(IssueDataModel.class)
                    .equalTo(ApiConst.STATE_FIELD_NAME, stateValue)
                    .findAllAsync();
        }
        realm.close();
        RealmRecyclerAdapter adapter = new RealmRecyclerAdapter(getContext(), mResults);
        mRecyclerView.setAdapter(adapter);
    }

    private String getRequestState(int tabNum) {
        switch (tabNum) {
            case TAB_ONE : return ApiConst.STATE_IN_PROGRESS;
            case TAB_TWO : return ApiConst.STATE_IN_DONE;
            case TAB_THREE : return ApiConst.STATE_IN_PENDING;
        }
        return null;
    }

    private String getStateValue(int tabNum) {
        switch (tabNum) {
            case TAB_ONE : return ApiConst.STATE_IN_PROGRESS_VALUE;
            case TAB_TWO : return ApiConst.STATE_IN_DONE_VALUE;
            case TAB_THREE : return ApiConst.STATE_IN_PENDING_VALUES;
        }
        return null;
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
                loadApiData(getRequestState(getTabNum()), ApiConst.TICKETS_AMOUNT, mLoadingTicketsAmount);
            }
        });
    }

    private void initSwipeToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearRealmDb();
                mLoadingTicketsAmount = 0;
                loadApiDataFirstPage();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void clearRealmDb() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }

    private class OnRecyclerItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {
        @Override
        public void onItemClick(View childView, int position) {
//            String text = getActivity().getString(R.string.intent_to_detail_recycler_msg)
//                    + (position + 1);
//            Intent openDetail = new Intent(getActivity(), DetailActivity.class);
//            openDetail.putExtra(getActivity().getString(R.string.intent_to_detail_extra_name), text);
//            getActivity().startActivity(openDetail);
            IssueDataModel itemModel = mResults.get(position);
            int modelId = itemModel.getId();
            Intent openDetail = new Intent(getActivity(), DetailActivity.class);
            openDetail.putExtra(getActivity().getString(R.string.intent_to_detail_extra_name), modelId);
            getActivity().startActivity(openDetail);
        }
    }
}