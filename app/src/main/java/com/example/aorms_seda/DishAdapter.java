package com.example.aorms_seda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishHolder> {


    private ArrayList<Dish> data;
    private int itemLayout;
    private int pos;

    public DishAdapter(ArrayList<Dish> data, int itemLayout, int pos)
    {   this.data=data;
        this.itemLayout=itemLayout;
        this.pos=pos;
    }

    @NonNull
    @Override
    public DishHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View V= LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new DishHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull DishHolder dishHolder, int i) {
        if(data!=null && dishHolder!=null)
        {
            dishHolder.setValues(data.get(i),pos);
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
