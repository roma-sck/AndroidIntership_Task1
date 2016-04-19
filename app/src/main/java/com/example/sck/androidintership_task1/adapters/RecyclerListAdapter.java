package com.example.sck.androidintership_task1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sck.androidintership_task1.activity.DetailActivity;
import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.model.ListItemModel;

import java.util.Collections;
import java.util.List;

/**
 * RecyclerListAdapter class, make a View for each item in the FragmentRecyclerList (ViewPager tab1, tab2)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.RecyclerAdapterHolder> {

    protected List<ListItemModel> mList = Collections.emptyList();
    private Context mContext;

    public RecyclerListAdapter(List<ListItemModel> list, Context context) {
        mList = list;
        mContext = context;
    }

    /**
     * creates a ViewHolder by inflating the CardView list item into the parent RecyclerView
     *
     * @param parent   RecyclerView
     * @param viewType type
     * @return ViewHolder
     */
    @Override
    public RecyclerAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_row, parent, false);
        RecyclerAdapterHolder holder = new RecyclerAdapterHolder(view);

        return holder;
    }

    /**
     * called to display list item in the RecyclerView list
     * set data to each list_item_row view from mList
     *
     * @param holder   RecyclerAdapterHolder
     * @param position current list item position
     */
    @Override
    public void onBindViewHolder(RecyclerAdapterHolder holder, int position) {

        holder.mImage.setImageResource(mList.get(position).getImage());
        holder.mLikeCount.setText(mList.get(position).getLikeCount());
        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mAddress.setText(mList.get(position).getAddress());
        holder.mDate.setText(mList.get(position).getDate());
        holder.mDaysLeft.setText(mList.get(position).getDaysLeft());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    /**
     * ViewHolder class
     */
    class RecyclerAdapterHolder extends RecyclerView.ViewHolder {

        private CardView mCardView;
        private ImageView mImage;
        private TextView mLikeCount;
        private TextView mTitle;
        private TextView mAddress;
        private TextView mDate;
        private TextView mDaysLeft;

        RecyclerAdapterHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cardView);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mLikeCount = (TextView) itemView.findViewById(R.id.like_count);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mAddress = (TextView) itemView.findViewById(R.id.address);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mDaysLeft = (TextView) itemView.findViewById(R.id.daysLeft);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String text = mContext.getString(R.string.intent_to_detail_recycler_hello_msg)
                            + (getAdapterPosition() + 1);

                    Intent openDetail = new Intent(mContext, DetailActivity.class);
                    openDetail.putExtra(mContext.getString(R.string.intent_to_detail_extra_name), text);
                    mContext.startActivity(openDetail);
                }
            });
        }

    }

}

