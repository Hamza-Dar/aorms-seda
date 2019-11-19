package com.example.aorms_seda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QueueParentAdapter extends RecyclerView.Adapter<ParentHolder> {

    Context context;
    ArrayList<VerticalModel> arrayList;


    public QueueParentAdapter(Context context, ArrayList<VerticalModel> data)
    {   this.context=context;
        this.arrayList=data;
    }

    @NonNull
    @Override
    public ParentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View V= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kitchen_parent_queue,viewGroup, false);
        return new ParentHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentHolder ParentHolder, int i) {

        VerticalModel verticalModel=arrayList.get(i);
        String title=verticalModel.getTitle();
        ArrayList<Dish> singleItem=verticalModel.getArrayList();
        ParentHolder.setValues(context,title,singleItem);

    }

    @Override
    public int getItemCount() {

        if (arrayList!=null)
            return arrayList.size();
        else
            return 0;
    }
}
