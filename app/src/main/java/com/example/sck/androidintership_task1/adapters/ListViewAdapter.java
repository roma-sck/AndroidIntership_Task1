package com.example.sck.androidintership_task1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.model.ListItemModel;

import java.util.Collections;
import java.util.List;

/**
 * ListViewAdapter class, make a View for each item in the FragmentListView (ViewPager tab3)
 */
public class ListViewAdapter extends BaseAdapter {
    protected List<ListItemModel> mList = Collections.emptyList();
    private Context mContext;

    public ListViewAdapter(List<ListItemModel> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position item position in the data set
     * @param convertView the old view to reuse, if possible
     * @param parent ListView
     * @return convertView
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewAdapterHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_row, parent, false);
            holder = new ListViewAdapterHolder();
            holder.mImage = (ImageView) convertView.findViewById(R.id.image);
            holder.mLikeCount = (TextView) convertView.findViewById(R.id.like_count);
            holder.mTitle = (TextView) convertView.findViewById(R.id.title);
            holder.mAddress = (TextView) convertView.findViewById(R.id.address);
            holder.mDate = (TextView) convertView.findViewById(R.id.date);
            holder.mDaysLeft = (TextView) convertView.findViewById(R.id.daysLeft);
            convertView.setTag(holder);
        } else {
            holder = (ListViewAdapterHolder) convertView.getTag();
        }
        holder.mImage.setImageResource(mList.get(position).getImage());
        holder.mLikeCount.setText(mList.get(position).getLikeCount());
        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mAddress.setText(mList.get(position).getAddress());
        holder.mDate.setText(mList.get(position).getDate());
        holder.mDaysLeft.setText(mList.get(position).getDaysLeft());

        return convertView;
    }

    /**
     * ViewHolder class
     */
    private static class ListViewAdapterHolder {
        private ImageView mImage;
        private TextView mLikeCount;
        private TextView mTitle;
        private TextView mAddress;
        private TextView mDate;
        private TextView mDaysLeft;
    }
}
