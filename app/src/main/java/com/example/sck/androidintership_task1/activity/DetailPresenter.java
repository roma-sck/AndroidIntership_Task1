package com.example.sck.androidintership_task1.activity;

import com.example.sck.androidintership_task1.models.IssueDataModel;

import io.realm.Realm;

public class DetailPresenter implements DetailContract.Presenter {
    private static final String ID_FIELD = "id";
    private DetailContract.View mView;

    public DetailPresenter(DetailContract.View view) {
        mView = view;
    }

    @Override
    public void loadModelById(int id) {
        Realm realm = Realm.getDefaultInstance();
        IssueDataModel model = realm.where(IssueDataModel.class)
                .equalTo(ID_FIELD, id)
                .findFirst();
        realm.close();
        mView.updateViews(model);
    }
}
