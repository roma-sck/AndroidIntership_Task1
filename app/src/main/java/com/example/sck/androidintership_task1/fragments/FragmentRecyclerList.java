package com.example.sck.androidintership_task1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.activity.DetailActivity;
import com.example.sck.androidintership_task1.adapters.RealmRecyclerAdapter;
import com.example.sck.androidintership_task1.api.ApiConst;
import com.example.sck.androidintership_task1.api.ApiController;
import com.example.sck.androidintership_task1.api.ApiService;
import com.example.sck.androidintership_task1.models.IssueDataModel;
import com.example.sck.androidintership_task1.utils.RecyclerItemClickListener;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FragmentRecyclerList extends Fragment {
    private static final String RECYCLER_KEY = "recycler_key";
    private ApiService mApiService;
    private RecyclerView mRecyclerView;
    private RealmConfiguration mRealmConfig;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRealmDb();
        mApiService = ApiController.getApiService();
        loadApiData(ApiConst.STATE_IN_PROGRESS, ApiConst.TICKETS_AMOUNT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        setUpRecyclerList(rootView);
        loadDataFromDb();
        return rootView;
    }

    private void initRealmDb() {
        if (mRealmConfig == null) {
            mRealmConfig = new RealmConfiguration.Builder(this.getActivity().getApplication().getApplicationContext())
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(mRealmConfig);
        }
        Realm.getDefaultInstance();
    }

    private void loadApiData(String state, int amount) {
        Observable<List<IssueDataModel>> observable = mApiService.loadDataRx(state, amount);
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
        Realm realm = Realm.getDefaultInstance();
        RealmResults<IssueDataModel> results = realm.where(IssueDataModel.class).findAllAsync();
        realm.close();
        RealmRecyclerAdapter adapter = new RealmRecyclerAdapter(getContext(), results);
        mRecyclerView.setAdapter(adapter);
    }

    private void setUpRecyclerList(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.appeals_recycler_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new OnRecyclerItemClickListener()));
    }

    private class OnRecyclerItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {
        @Override
        public void onItemClick(View childView, int position) {
            String text = getActivity().getString(R.string.intent_to_detail_recycler_msg)
                    + (position + 1);
            Intent openDetail = new Intent(getActivity(), DetailActivity.class);
            openDetail.putExtra(getActivity().getString(R.string.intent_to_detail_extra_name), text);
            getActivity().startActivity(openDetail);
        }
    }
}