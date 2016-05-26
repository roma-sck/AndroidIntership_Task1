package com.example.sck.androidintership_task1.models.temp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class DataModel implements Parcelable {
    private List<ListItemModel> mData;

    public DataModel(List<ListItemModel> data) {
        mData = data;
    }

    public List<ListItemModel> getData() {
        return mData;
    }

    /**
     * Retrieving DataModel data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    protected DataModel(Parcel in) {
        if (in.readByte() == 0x01) {
            mData = new ArrayList<>();
            in.readList(mData, ListItemModel.class.getClassLoader());
        } else {
            mData = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the DataModel data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mData == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mData);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DataModel> CREATOR = new Parcelable.Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };
}
