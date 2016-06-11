package com.example.sck.androidintership_task1.activity;

import com.example.sck.androidintership_task1.models.IssueDataModel;

public interface DetailContract {
    interface Presenter {
        void attachView(View view);
        void loadModelById(int id);
        void detachView();
    }

    interface View {
        void updateViews(IssueDataModel model);
    }
}
