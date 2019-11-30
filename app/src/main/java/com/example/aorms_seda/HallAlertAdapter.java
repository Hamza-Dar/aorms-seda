package com.example.aorms_seda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class HallAlertAdapter extends FirestoreRecyclerAdapter<hall_alert,HallAlertAdapter.OrderListViewHolder > {
    public HallAlertAdapter(FirestoreRecyclerOptions<hall_alert> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(HallAlertAdapter.OrderListViewHolder orderListViewHolder, int i, hall_alert hall_manager_new_order_list_item) {
        orderListViewHolder.orderIdTextView.setText("New Order " +hall_manager_new_order_list_item.getOrder()+" is Added");
    }


    @NonNull
    @Override
    public HallAlertAdapter.OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alerts,
                parent,false);
        return new HallAlertAdapter.OrderListViewHolder(v);
    }

    class OrderListViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.orderIdTextView = itemView.findViewById(R.id.orderadded);
        }
    }
}