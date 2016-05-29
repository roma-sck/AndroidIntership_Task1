package com.example.sck.androidintership_task1.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.models.IssueDataModel;
import com.example.sck.androidintership_task1.utils.DateConverter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * RecyclerListAdapter class, make a View for each item in the FragmentRecyclerList
 */
public class RealmRecyclerAdapter extends RealmRecyclerViewAdapter<IssueDataModel, RealmRecyclerAdapter.ViewHolder>  {
    private Context mContext;

    public RealmRecyclerAdapter(Context context, OrderedRealmCollection<IssueDataModel> data) {
        super(context, data, true);
        mContext = context;
    }

    /**
     * creates a ViewHolder by inflating the CardView list item into the parent RecyclerView
     *
     * @param parent   RealmRecyclerViewAdapter
     * @param viewType type
     * @return ViewHolder
     */
    @Override
    public RealmRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * called to display list item in the RecyclerView list
     * set data to each list_item_row view from mList
     *
     * @param holder   RealmRecyclerAdapter.ViewHolder
     * @param position current list item position
     */
    @Override
    public void onBindViewHolder(RealmRecyclerAdapter.ViewHolder holder, int position) {
        IssueDataModel dataModel = getData().get(position);
        holder.mImage.setImageResource(R.mipmap.ic_doc);
        holder.mLikeCount.setText(String.valueOf(dataModel.getLikesCounter()));
        holder.mTitle.setText(dataModel.getTitle());
        String streetType = dataModel.getUser().getAddress().getStreet().getStreetType().getShortName();
        String streetName = dataModel.getUser().getAddress().getStreet().getName();
        String address = streetType + " " + streetName;
        holder.mAddress.setText(address);
        String date = DateConverter.convertDate(dataModel.getCreatedDate());
        holder.mDate.setText(date);
        String daysLeft = DateConverter.getDaysLeft(dataModel.getStartDate());
        holder.mDaysLeft.setText(daysLeft);
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image) ImageView mImage;
        @BindView(R.id.like_count) TextView mLikeCount;
        @BindView(R.id.title) TextView mTitle;
        @BindView(R.id.address) TextView mAddress;
        @BindView(R.id.date) TextView mDate;
        @BindView(R.id.daysLeft) TextView mDaysLeft;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
