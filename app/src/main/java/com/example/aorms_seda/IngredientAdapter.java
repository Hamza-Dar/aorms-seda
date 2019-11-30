package com.example.aorms_seda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aorms_seda.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    Context context;
    View v;

    private List<Ingredient> data;

    public IngredientAdapter(List<Ingredient> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_list_layout, parent, false);
        v = view;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.name.setText(data.get(position).name);
        viewHolder.id.setText(data.get(position).id);
        viewHolder.quantity.setText("" + data.get(position).quantity);
        viewHolder.threshold.setText("" + data.get(position).threshold);
        viewHolder.price.setText("" + data.get(position).price);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name, id, quantity, threshold, price;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.ingredient_name);
            id = (TextView)itemView.findViewById(R.id.ingredient_id);
            quantity = (TextView) itemView.findViewById(R.id.ingredient_quantity);
            threshold = (TextView) itemView.findViewById(R.id.ingredient_threshold);
            price = itemView.findViewById(R.id.ingredient_price);
        }
    }
}