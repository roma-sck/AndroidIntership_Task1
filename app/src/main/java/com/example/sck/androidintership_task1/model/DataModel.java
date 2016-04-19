package com.example.sck.androidintership_task1.model;

import java.io.Serializable;
import java.util.List;

public class DataModel implements Serializable {

    private List<ListItemModel> mData;

    public List<ListItemModel> getData() {
        return mData;
    }

    public void setData(List<ListItemModel> mData) {
        this.mData = mData;
    }
}
