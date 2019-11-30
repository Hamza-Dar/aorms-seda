package com.example.aorms_seda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChangeDishAdapter extends RecyclerView.Adapter<ChangeDishHolder> {


    private ArrayList<RequestsChange> data;
    private int itemLayout;
    Context context;

    public ChangeDishAdapter(ArrayList<RequestsChange> data, int itemLayout)
    {   this.data=data;
        this.itemLayout=itemLayout;

    }

    @NonNull
    @Override
    public ChangeDishHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View V= LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        context=V.getContext();
        return new ChangeDishHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeDishHolder dishHolder, int i) {
        if(data!=null && dishHolder!=null)
        {
            dishHolder.setValues(context,data.get(i));
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

