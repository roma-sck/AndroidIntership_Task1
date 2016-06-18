package com.example.sck.androidintership_task1.fragments;

import android.content.Context;

import com.example.sck.androidintership_task1.activity.MainPresenter;
import com.example.sck.androidintership_task1.adapters.RealmRecyclerAdapter;
import com.example.sck.androidintership_task1.api.ApiConst;
import com.example.sck.androidintership_task1.api.ApiController;
import com.example.sck.androidintership_task1.api.ApiService;
import com.example.sck.androidintership_task1.models.IssueDataModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FragmentPresenter implements FragmentContract.Presenter {
    private Context mContext;
    private FragmentContract.View mView;
    private int mTabNum;
    private ApiService mApiService;
    private RealmConfiguration mRealmConfig;
    private RealmResults<IssueDataModel> mResults;
    private String mRequestState;

    public FragmentPresenter(Context context, int tabNum) {
        mContext = context;
        mTabNum = tabNum;
        mApiService = ApiController.getApiService();
        mRequestState = getRequestState(tabNum);
    }

    @Override
    public void attachView(FragmentContract.View view) {
        mView = view;
    }

    @Override
    public void initRealmDb() {
        if (mRealmConfig == null) {
            mRealmConfig = new RealmConfiguration
                    .Builder(mContext.getApplicationContext())
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(mRealmConfig);
        }
        Realm.getDefaultInstance();
    }

    @Override
    public void loadApiDataFirstPage() {
        loadApiData(mRequestState, ApiConst.TICKETS_AMOUNT, ApiConst.TICKETS_WITHOUT_OFFSET);
    }

    @Override
    public void loadApiDataFirstPageAllTabs() {
        loadApiData(getRequestState(MainPresenter.TAB_ONE), ApiConst.TICKETS_AMOUNT, ApiConst.TICKETS_WITHOUT_OFFSET);
        loadApiData(getRequestState(MainPresenter.TAB_TWO), ApiConst.TICKETS_AMOUNT, ApiConst.TICKETS_WITHOUT_OFFSET);
        loadApiData(getRequestState(MainPresenter.TAB_THREE), ApiConst.TICKETS_AMOUNT, ApiConst.TICKETS_WITHOUT_OFFSET);
    }

    @Override
    public void loadApiDataNextPage(int offset) {
        loadApiData(mRequestState, ApiConst.TICKETS_AMOUNT, offset);
    }

    private String getRequestState(int tabNum) {
        switch (tabNum) {
            case MainPresenter.TAB_ONE : return ApiConst.STATE_IN_PROGRESS;
            case MainPresenter.TAB_TWO : return ApiConst.STATE_IN_DONE;
            case MainPresenter.TAB_THREE : return ApiConst.STATE_IN_PENDING;
        }
        return null;
    }

    private String getStateValue(int tabNum) {
        switch (tabNum) {
            case MainPresenter.TAB_ONE : return ApiConst.STATE_IN_PROGRESS_VALUE;
            case MainPresenter.TAB_TWO : return ApiConst.STATE_IN_DONE_VALUE;
            case MainPresenter.TAB_THREE : return ApiConst.STATE_IN_PENDING_VALUES;
        }
        return null;
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
                        mView.showErrorMessage(e.getMessage());
                    }
                    @Override
                    public void onNext(List<IssueDataModel> issues) {
                        saveToRealmDb(issues);
                    }
                });
        mView.hideSwipeRefreshProgress(false);
    }

    private void saveToRealmDb(List<IssueDataModel> issues) {
        Realm realm = Realm.getDefaultInstance();
        // add content to the Relam DB
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(issues);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void loadDataFromDb() {
        String stateValue = getStateValue(mTabNum);
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
        RealmRecyclerAdapter adapter = new RealmRecyclerAdapter(mContext, mResults);
        mView.setRealmAdapter(adapter);
    }

    @Override
    public void clearRealmDb() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public int getClickedItemId(int itemPosition) {
        IssueDataModel itemModel = mResults.get(itemPosition);
        return itemModel.getId();
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
