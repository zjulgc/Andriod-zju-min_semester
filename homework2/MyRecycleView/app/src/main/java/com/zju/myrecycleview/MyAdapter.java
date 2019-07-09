package com.zju.myrecycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {
    private ArrayList<String> mydata;

    public MyAdapter(ArrayList<String> data)
    {
        mydata = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item, parent, false);
        Viewerholder viewerholder = new Viewerholder(v);
        return viewerholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Viewerholder)holder).mtv.setText(mydata.get(position));
    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }

    public static class Viewerholder extends RecyclerView.ViewHolder
    {
        TextView mtv;
        public Viewerholder(@NonNull View itemView) {
            super(itemView);
            mtv = (TextView)itemView.findViewById(R.id.my_tv0);
        }
    }
}
