package com.example.sck.androidintership_task1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder> {

    private Context mContext;
    private String[] mDataSet;
    private static final String ASSETS_URL = "file:///android_asset/";

    public RecyclerViewAdapter(Context context, String[] dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // creates a ViewHolder by inflating the ImageView into the parent RecyclerView
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        // called to display an image in the RecyclerView list
        Picasso.with(mContext)
                .load(ASSETS_URL + mDataSet[position])
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        // ViewHolder contains a single ImageView
        private ImageView mImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.myImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = mContext.getString(R.string.toast_text) + (getAdapterPosition()
                            + 1) + "\n " + (mDataSet[getAdapterPosition()]);

                    Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
