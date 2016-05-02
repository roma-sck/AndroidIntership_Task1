package com.example.sck.androidintership_task1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.activity.DetailActivity;
import com.example.sck.androidintership_task1.adapters.RecyclerListAdapter;
import com.example.sck.androidintership_task1.model.ListItemModel;
import com.example.sck.androidintership_task1.model.DataModel;
import com.example.sck.androidintership_task1.utils.RecyclerItemClickListener;

import java.util.List;

public class FragmentRecyclerList extends Fragment {
    private static final String RECYCLER_KEY = "recycler_key";
    private List<ListItemModel> mData;

    public FragmentRecyclerList() {
        // required empty constructor
    }

    /**
     * Static factory method that takes an DataModel parameter,
     * initializes the fragment's arguments, and returns the
     * new fragment to the client.
     */
    public static FragmentRecyclerList getInstance(DataModel mModel) {
        FragmentRecyclerList fragment = new FragmentRecyclerList();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECYCLER_KEY, mModel);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_list, container, false);
        // Fetching data from a parcelable object passed from MainActivity
        Bundle bundle = getArguments();
        DataModel model = bundle.getParcelable(RECYCLER_KEY);
        if(model != null) {
            mData = model.getData();
        }
        setUpRecyclerList(rootView);

        return rootView;
    }

    private void setUpRecyclerList(View rootView) {
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.appeals_recycler_list);
        RecyclerListAdapter adapter = new RecyclerListAdapter(mData, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new OnRecyclerItemClickListener()));
    }

    private class OnRecyclerItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {
        @Override
        public void onItemClick(View childView, int position) {
            String text = getActivity().getString(R.string.intent_to_detail_recycler_msg)
                    + (position + 1);
            Intent openDetail = new Intent(getActivity(), DetailActivity.class);
            openDetail.putExtra(getActivity().getString(R.string.intent_to_detail_extra_name), text);
            getActivity().startActivity(openDetail);
        }
    }

}