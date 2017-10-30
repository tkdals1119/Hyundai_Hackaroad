package com.sourcey.Hackaroad.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourcey.Hackaroad.R;

import java.util.ArrayList;

/**
 * Created by Jeong on 2017-10-29.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<Recycler_item> mitems;

    Context mContext;
    RecyclerItemClickListener recyclerItemClickListener;



    public RecyclerAdapter(Context mContext, ArrayList<Recycler_item> items, RecyclerItemClickListener listener) {
        mitems = items;
        this.mContext = mContext;
        this.recyclerItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerItemClickListener.OnItemClick(view, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.date.setText(mitems.get(position).getDate());
        holder.content.setText(mitems.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView content;


        public ViewHolder(View itemView){
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.textView_date);
            content = (TextView)itemView.findViewById(R.id.textView_contents);

        }


    }
}
