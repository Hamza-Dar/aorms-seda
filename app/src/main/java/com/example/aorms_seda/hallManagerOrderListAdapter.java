package com.example.aorms_seda;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class hallManagerOrderListAdapter extends FirestoreRecyclerAdapter<hall_manager_new_order_list_item,hallManagerOrderListAdapter.OrderListViewHolder > {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public hallManagerOrderListAdapter(FirestoreRecyclerOptions<hall_manager_new_order_list_item> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(OrderListViewHolder orderListViewHolder, int i, hall_manager_new_order_list_item hall_manager_new_order_list_item) {
        orderListViewHolder.orderStatusTextView.setText(hall_manager_new_order_list_item.getStatus());
        orderListViewHolder.orderIdTextView.setText(String.valueOf(hall_manager_new_order_list_item.getTable()));
        orderListViewHolder.orderTimeTextView.setText(String.valueOf(hall_manager_new_order_list_item.getTime()));


    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_new_order_hall_manager,
        parent,false);
        return new OrderListViewHolder(v);
    }

    class OrderListViewHolder extends RecyclerView.ViewHolder{
        TextView orderIdTextView;
        TextView orderStatusTextView;
        TextView orderTimeTextView;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.orderIdTextView = itemView.findViewById(R.id.order_id);
            this.orderStatusTextView= itemView.findViewById(R.id.order_status);
            this.orderTimeTextView=itemView.findViewById(R.id.time_status);

        }
    }
}
