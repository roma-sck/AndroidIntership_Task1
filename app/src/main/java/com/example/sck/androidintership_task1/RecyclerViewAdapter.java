package com.example.sck.androidintership_task1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private String[] mDataSet;

    public RecyclerViewAdapter(Context context, String[] dataSet) {
        this.mContext = context;
        this.mDataSet = dataSet;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(mDataSet[position])
                .into(holder.myImage);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView myImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            myImage = (ImageView) itemView.findViewById(R.id.myImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, " RecyclerView's image №" +(getAdapterPosition() + 1) + ": " +
                            (mDataSet[getAdapterPosition()]), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
