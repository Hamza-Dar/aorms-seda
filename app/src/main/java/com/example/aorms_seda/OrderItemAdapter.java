package com.example.aorms_seda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
    Context mContext;
    List<OrderItem> orderItemList;
    String item_name;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_queue_item, parent, false);
        OrderItemViewHolder orderItemVH = new OrderItemViewHolder(v);
        return orderItemVH;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, final int position) {
        holder.name.setText(this.orderItemList.get(position).getName());
        holder.price.setText(Integer.toString(this.orderItemList.get(position).getPrice()));
        holder.quantity.setText(Integer.toString(this.orderItemList.get(position).getQuantity()));
        holder.update_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item_name = orderItemList.get(position).getName();
                OrderedItemsQueue queue = OrderedItemsQueue.Singleton();
                if(queue.orderItemList.size() > 0) {
                    final List<OrderItem> items = queue.getOrderItemList();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    if (queue.orderId != null) {
                        db.collection("Orders").document(queue.orderId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap> items1 = (List<HashMap>) documentSnapshot.get("Items");
                                for (HashMap i : items1) {
                                    if (item_name.equals(i.get("itemName"))) {
                                        if (i.get("itemStatus").equals("waiting")) {
                                            //allow update
                                            for (int x = 0; x < items.size(); x++) {
                                                boolean isEdited = false;
                                                items.get(x).status = i.get("itemStatus").toString();
                                                if (items.get(x).getName().equals(item_name)) {
                                                    items.remove(x);
                                                    transition(items);
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
        Log.d(TAG, "onBindViewHolder: ");
    }
    public void transition(List<OrderItem> items){
        // start the activity
        Intent intent = new Intent(mContext, FoodOrderMainMenu.class);
        mContext.startActivity(intent);
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView quantity;
        Button update_order;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            update_order = (Button) itemView.findViewById(R.id.UpdateCartOrder);
            name = (TextView) itemView.findViewById(R.id.queue_item_name);
            price = (TextView) itemView.findViewById(R.id.queue_item_price);
            quantity = (TextView) itemView.findViewById(R.id.queue_item_quantity);
        }
    }
}



