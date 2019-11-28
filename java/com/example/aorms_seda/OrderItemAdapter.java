package com.example.aorms_seda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
    Context mContext;
    List<OrderItem> orderItemList;

    public OrderItemAdapter(Context mContext, List<OrderItem> orderItemList) {
        this.mContext = mContext;
        this.orderItemList = orderItemList;
    }

    @Override
    public int getItemCount() {
        return this.orderItemList.size();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_queue_item, parent,false);
        OrderItemViewHolder orderItemVH = new OrderItemViewHolder(v);
        return orderItemVH;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        holder.name.setText(this.orderItemList.get(position).getName());
        holder.price.setText(this.orderItemList.get(position).getPrice());
        holder.quantity.setText(this.orderItemList.get(position).getQuantity());

    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView quantity;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.queue_item_name);
            price = (TextView) itemView.findViewById(R.id.queue_item_price);
            quantity = (TextView) itemView.findViewById(R.id.queue_item_quantity);
        }
    }
}



