package com.sourcey.Hackaroad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourcey.Hackaroad.R;

import java.util.ArrayList;

/**
 * Created by BSM on 2017-10-26.
 */

public class Frag_ListActivity extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Recycler_item> mMyData;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_frag_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));

        mRecyclerView.scrollToPosition(0);
        mAdapter = new RecyclerAdapter(getActivity(), mMyData, new RecyclerItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Log.d("클릭", "Clicked Position" + position);
                Intent intent = new Intent(getContext(), VideoViewActivity.class);
                intent.putExtra("data", mMyData.get(position).date);
                intent.putExtra("content", mMyData.get(position).content);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataSet();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void initDataSet(){
        // for Test
        mMyData = new ArrayList<>();
        Recycler_item[] item = new Recycler_item[10];
        item[0]=new Recycler_item("2017-10-26", "정지선 침범");
        item[1]=new Recycler_item("2017-10-26", "방지턱 속도 감소 미시행");
        item[2]=new Recycler_item("2017-10-27", "방향 지시등");
        item[3]=new Recycler_item("2017-10-27", "정지선 침범");
        item[4]=new Recycler_item("2017-10-28", "커브길 속도 감소 미시행");
        item[5]=new Recycler_item("2017-10-26", "정지선 침범");
        item[6]=new Recycler_item("2017-10-26", "방지턱 속도 감소 미시행");
        item[7]=new Recycler_item("2017-10-27", "방향 지시등");
        item[8]=new Recycler_item("2017-10-27", "정지선 침범");
        item[9]=new Recycler_item("2017-10-28", "커브길 속도 감소 미시행");

        for(int i=0; i<10; i++) mMyData.add(item[i]);
    }
}
