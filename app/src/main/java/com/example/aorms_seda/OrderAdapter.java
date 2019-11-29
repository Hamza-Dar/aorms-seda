package com.example.aorms_seda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

    private ArrayList<Order> data;
    private int itemlayout;

    public OrderAdapter(ArrayList<Order> data, int itemLayout)
    {   this.data=data;
        this.itemlayout=itemLayout;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V= LayoutInflater.from(parent.getContext()).inflate(itemlayout,parent, false);
        return new OrderHolder(V);

    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {

        if(data!=null && holder!=null)
        {
            holder.setValues(data.get(position));
        }

    }

    @Override
    public int getItemCount() {
        if (data!=null)
            return data.size();
        else
            return 0;
    }
}
