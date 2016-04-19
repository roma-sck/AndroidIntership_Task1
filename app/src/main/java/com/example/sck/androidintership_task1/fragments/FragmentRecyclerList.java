package com.example.sck.androidintership_task1.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.adapters.RecyclerListAdapter;
import com.example.sck.androidintership_task1.model.ListItemModel;
import com.example.sck.androidintership_task1.model.DataModel;

import java.util.List;

public class FragmentRecyclerList extends Fragment {

    private static final String RECYCLER_KEY = "recycler_key";
    private List<ListItemModel> mData;

    public FragmentRecyclerList() {
        // empty constructor
    }

    /**
     * Static factory method that takes an SerializableModel parameter,
     * initializes the fragment's arguments, and returns the
     * new fragment to the client.
     */
    public static FragmentRecyclerList getInstance(DataModel mModel) {

        FragmentRecyclerList fragment = new FragmentRecyclerList();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RECYCLER_KEY, mModel);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recycler_list, container, false);

        DataModel model = (DataModel) getArguments().getSerializable(RECYCLER_KEY);
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
    }

}