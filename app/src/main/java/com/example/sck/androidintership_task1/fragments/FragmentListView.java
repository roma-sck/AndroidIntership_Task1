package com.example.sck.androidintership_task1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sck.androidintership_task1.activity.DetailActivity;
import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.adapters.ListViewAdapter;
import com.example.sck.androidintership_task1.model.ListItemModel;
import com.example.sck.androidintership_task1.model.SerializableModel;

import java.util.List;

public class FragmentListView extends Fragment {

    private static final String LISTVIEW_KEY = "listview_key";
    private List<ListItemModel> mData;

    public FragmentListView() {
    }

    public static FragmentListView getInstance(SerializableModel mModel) {

        FragmentListView fragment = new FragmentListView();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LISTVIEW_KEY, mModel);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list_view, container,
                false);

        SerializableModel model = (SerializableModel) getArguments().getSerializable(LISTVIEW_KEY);
        if(model != null) {
            mData = model.getData();
        }

        setUpListView(rootView);

        return rootView;
    }

    private void setUpListView(View rootView) {

        final ListView listView = (ListView) rootView.findViewById(R.id.appeals_list);
        ListViewAdapter adapter = new ListViewAdapter(mData, getActivity());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = "Hello from ListView list _pos:" + (position + 1);

                Intent openDetail = new Intent(getActivity(), DetailActivity.class);
                openDetail.putExtra(getActivity().getString(R.string.intent_to_detail_extra_name), text);
                getActivity().startActivity(openDetail);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            FloatingActionButton action_button = (FloatingActionButton) getActivity().findViewById(R.id.action_button);
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(mLastFirstVisibleItem<firstVisibleItem)
                {
                    action_button.hide();
                }
                if(mLastFirstVisibleItem>firstVisibleItem)
                {
                    action_button.show();
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

}