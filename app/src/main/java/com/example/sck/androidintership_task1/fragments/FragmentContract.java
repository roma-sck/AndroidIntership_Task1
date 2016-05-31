package com.example.sck.androidintership_task1.fragments;

import com.example.sck.androidintership_task1.adapters.RealmRecyclerAdapter;

public interface FragmentContract {
    interface Presenter {
        void initRealmDb();
        void loadApiDataFirstPage();
        void loadDataFromDb();
        void loadApiDataNextPage(int offset);
        void clearRealmDb();
        int getClickedItemId(int itemPosition);
    }

    interface View {
        void setRealmAdapter(RealmRecyclerAdapter adapter);
        void showErrorMessage(String message);
        void hideSwipeRefreshProgress(boolean progress);
    }
}
