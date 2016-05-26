package com.example.sck.androidintership_task1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.activity.DetailActivity;
import com.example.sck.androidintership_task1.adapters.RecyclerListAdapter;
import com.example.sck.androidintership_task1.api.ApiConst;
import com.example.sck.androidintership_task1.api.ApiController;
import com.example.sck.androidintership_task1.api.ApiService;
import com.example.sck.androidintership_task1.models.IssueDataModel;
import com.example.sck.androidintership_task1.models.temp.ListItemModel;
import com.example.sck.androidintership_task1.models.temp.DataModel;
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

    private static final String LOG_TAG = FragmentRecyclerList.class.getSimpleName();
    private static final String RECYCLER_KEY = "recycler_key";
    private Realm mRealm;
    private ApiService mApiService;

    private List<ListItemModel> mData;

    public FragmentRecyclerList() {
        // required empty constructor
    }

    /**
     * Static factory method that takes an DataModel parameter,
     * initializes the fragment's arguments, and returns the
     * new fragment to the client.
     */
    public static FragmentRecyclerList getInstance(DataModel mModel) {
        FragmentRecyclerList fragment = new FragmentRecyclerList();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECYCLER_KEY, mModel);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        // Fetching data from a parcelable object passed from MainActivity
        Bundle bundle = getArguments();
        DataModel model = bundle.getParcelable(RECYCLER_KEY);
        if(model != null) {
            mData = model.getData();
        }
        setUpRecyclerList(rootView);

        //------------------------------------------------------------------
        initRealmDb();
        mApiService = ApiController.getApiService();
        loadApiData(ApiConst.STATE_IN_PROGRESS, ApiConst.TICKETS_AMOUNT, 0);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadApiData(ApiConst.STATE_IN_PROGRESS, ApiConst.TICKETS_AMOUNT, 20);
        //------------------------------------------------------------------

        return rootView;
    }

    private void initRealmDb() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).build();
        Realm.deleteRealm(realmConfig); // Delete Realm between app restarts.
        Realm.setDefaultConfiguration(realmConfig);
        // Create a new empty instance of Realm
        mRealm = Realm.getDefaultInstance();
    }

    private void loadApiData(String state, int amount, int offcet) {
        Observable<List<IssueDataModel>> observable = mApiService.loadDataRx(state, amount, offcet);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<IssueDataModel>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(LOG_TAG, "------------onCompleted----------");
                        loadDataFromDb();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(LOG_TAG, "------------onError----------");
                        e.printStackTrace();
                        Log.i(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<IssueDataModel> issues) {
                        Log.i(LOG_TAG, "------------onNext----------");
                        // add content to the Relam DB
                        // Open a transaction to store items into the realm
                        // Use copyToRealm() to convert the objects into proper RealmObjects managed by Realm.
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(issues);
                        Log.i(LOG_TAG, "-----------DATA COPY TO REALM----------");
                        mRealm.commitTransaction();
                        Log.i(LOG_TAG, "-----------DATA COMMIT TO REALM----------");
                    }
                });
    }

    private void loadDataFromDb() {
        Log.i(LOG_TAG, "-----------loadDataFromDb----------");
        RealmResults<IssueDataModel> results = mRealm.where(IssueDataModel.class).findAll();
        for (int i = 0; i < results.size(); i++) {
            Log.i(LOG_TAG, "issue1 # " + i + "\n" +
                    "item(" + i + ") issues.get(i).getId() = " + results.get(i).getId() + "\n" +
                    "item(" + i + ") issues.get(i).getTitle() = " + results.get(i).getTitle() + "\n" +
                    "item(" + i + ") issues.get(i).getLikesCounter() = " + results.get(i).getLikesCounter() + "\n" +
                    "item(" + i + ") issues.get(i).getCreatedDate() = " + results.get(i).getCreatedDate() + "\n" +
                    "item(" + i + ") issues.get(i).getUser().getFirstName() = " + results.get(i).getUser().getFirstName() + "\n" +
                    "item(" + i + ") issues.get(i).getUser().getLastName() = " + results.get(i).getUser().getLastName() + "\n" +
                    "item(" + i + ") issues.get(i).getUser().getAddress().getStreet().getName() = " + results.get(i).getUser().getAddress().getStreet().getName() + "\n" );
        }
    }

    private void setUpRecyclerList(View rootView) {
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.appeals_recycler_list);
        RecyclerListAdapter adapter = new RecyclerListAdapter(mData, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
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