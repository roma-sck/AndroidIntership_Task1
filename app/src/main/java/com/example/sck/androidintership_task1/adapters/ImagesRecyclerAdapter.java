package com.example.sck.androidintership_task1.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.api.ApiConst;
import com.example.sck.androidintership_task1.models.File;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesRecyclerAdapter extends RecyclerView.Adapter<ImagesRecyclerAdapter.ImageViewHolder> {
    private Context mContext;
    private List<File> mDataSet;

    public ImagesRecyclerAdapter(Context context, List<File> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    /**
     * creates a ViewHolder by inflating the ImageView into the parent RecyclerView
     *
     * @param parent   RecyclerView
     * @param viewType type
     * @return ViewHolder
     */
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    /**
     * called to display an image in the RecyclerView list
     * images load by Picasso library
     *
     * @param holder   ImageViewHolder
     * @param position current img position
     */
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        String picture = "";
        if(mDataSet != null) picture = mDataSet.get(position).getFilename();
        Picasso.with(mContext)
                .load(ApiConst.TICKETS_IMGS_URL + picture)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        if(mDataSet != null) {
            return mDataSet.size();
        }
        return 0;
    }

    /**
     * ViewHolder class, contains a single ImageView
     */
    class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.detail_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = mContext.getString(R.string.toast_text) + (getAdapterPosition()
                            + 1) + "\n " + (mDataSet.get(getAdapterPosition()));
                    Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
