package com.example.sck.androidintership_task1.activity;

import com.example.sck.androidintership_task1.models.IssueDataModel;

public interface DetailContract {
    interface Presenter {
        void loadModelById(int id);
    }

    interface View {
        void updateViews(IssueDataModel model);
    }
}
