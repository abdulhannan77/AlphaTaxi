package com.example.hanan.alphataxi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by hanan on 1/13/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<RowData> itemList;
    private Context context;
    public static final int VISIBLE_ITEMS = 2;
    public static final int NON_VISIBLE_ITEMS = 7;
    private static final int TOTAL_ITEMS = VISIBLE_ITEMS + NON_VISIBLE_ITEMS * 2;
    public static final int ITEM_IN_CENTER = (int)Math.ceil(VISIBLE_ITEMS / 2f) + NON_VISIBLE_ITEMS;


    public RecyclerViewAdapter(Context context, List<RowData> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("RecyclingTest", "onCreateViewHolder method is called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position){
        Log.d("RecyclingTest","onBindViewHolder method is called");
        holder.userName.setText(itemList.get(position).getpercent());

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
