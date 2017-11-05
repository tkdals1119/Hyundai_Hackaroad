package com.sourcey.Hackaroad.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sourcey.Hackaroad.R;
import com.sourcey.Hackaroad.model.Case_List;
import com.sourcey.Hackaroad.utils.ApiRequester;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by BSM on 2017-10-26.
 */

public class Frag_ListActivity extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Recycler_item> mMyData;

    private String[] list_arr;
    private String[] list_arr_date;

    private String[] list_habit_arr;
    private String[] list_habit_date_arr;


    Activity root = getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requestCaseList();

        View view = inflater.inflate(R.layout.activity_frag_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5));
        mRecyclerView.scrollToPosition(0);

        return view;
    }

    private void putDataToAdapter() {
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
    }

    public void setMyData(String[] list_arr) {
        this.list_habit_arr = list_arr;
    }

    public void setMyDataDAte(String[] list_arr_date) {
        this.list_habit_date_arr = list_arr_date;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void requestCaseList() {
        ApiRequester.getInstance().getList(new ApiRequester.UserCallback<List<Case_List>>() {
            @Override
            public void onSuccess(List<Case_List> result) {
                if(result==null)
                {
                    Toast.makeText(root, "정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int size = result.size();
                    list_arr = new String[size];
                    list_arr_date = new String[size];
                    int count = 0;

                    for(Case_List list : result)
                    {
                        list_arr[count] = list.gethabbitname();

                        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");

                        Date date = null;
                        try {
                            date = sourceFormat.parse(list.getcreated_at());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedDate = destFormat.format(date);
                        list_arr_date[count] = formattedDate;

                        count++;
                    }
                    setMyData(list_arr);
                    setMyDataDAte(list_arr_date);

                    initDataSet();
                    putDataToAdapter();
                }
            }
            @Override
            public void onFail() {
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initDataSet(){
        int size = list_habit_arr.length;

        mMyData = new ArrayList<>();
        Recycler_item[] item = new Recycler_item[size];

        for(int i=0; i<size; i++)
        {
            item[i]=new Recycler_item(list_habit_date_arr[i], list_habit_arr[i], R.drawable.ic_navigate_next_black_48dp);
        }
        for(int i=0; i<size; i++) mMyData.add(item[i]);
    }
}
