package com.example.aorms_seda;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    Context context;
    View v;

    private ArrayList<FoodItem> data;

    public MenuAdapter(ArrayList<FoodItem> data){
        this.data = data;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.menu_list_layout, parent, false);
        v = view;
        return new MenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.name.setText(data.get(position).name);
        viewHolder.id.setText(data.get(position).id);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FoodItemDetails.class);
                i.putExtra("FoodItem", data.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name, id;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.dish_name);
            id = (TextView)itemView.findViewById(R.id.dish_id);
        }
    }
}
