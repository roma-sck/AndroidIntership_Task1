package com.example.sck.androidintership_task1.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class LoadMoreRecyclerScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below current scroll position before loading more.
    private static final int VISIBLE_THRESHHOLD = 4;
    // The current offset index of data you have loaded
    private int mCurrentPage = 0;
    // The total number of items in the dataset after the last load
    private int mPreviousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean mLoading = true;
    private RecyclerView.LayoutManager mLayoutManager;

    public LoadMoreRecyclerScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int startingPageIndex = 0;
        int totalItemCount = mLayoutManager.getItemCount();
        int lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        // if the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < mPreviousTotalItemCount) {
            this.mCurrentPage = startingPageIndex;
            this.mPreviousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.mLoading = true;
            }
        }
        // if the dataset count has changed, update the current page number and total item count.
        if (mLoading && (totalItemCount > mPreviousTotalItemCount)) {
            mLoading = false;
            mPreviousTotalItemCount = totalItemCount;
        }
        // check to see if we have breached the visibleThreshold and need to reload more data.
        if (!mLoading && (lastVisibleItemPosition + VISIBLE_THRESHHOLD) > totalItemCount) {
            mCurrentPage++;
            onLoadMore(mCurrentPage, totalItemCount);
            mLoading = true;
        }
    }

    // defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount);
}
