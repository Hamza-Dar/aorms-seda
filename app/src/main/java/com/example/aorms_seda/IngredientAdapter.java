package com.example.aorms_seda;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    Context mContext;
    List<Ingredient> ingredientList;

    public IngredientAdapter(Context mContext, List<Ingredient> ingredientList) {
        this.mContext = mContext;
        this.ingredientList = ingredientList;
    }

    @Override
    public int getItemCount() {
        return this.ingredientList.size();
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View ingredientView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ingredient, parent, false);
        IngredientViewHolder ivh = new IngredientViewHolder(ingredientView);
        return ivh;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, final int position) {
        holder.name.setText(this.ingredientList.get(position).getName());

    }

    class IngredientViewHolder extends RecyclerView.ViewHolder{

        TextView name;


        public IngredientViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.ingredientName);
        }
    }
}
