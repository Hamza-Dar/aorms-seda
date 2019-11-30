package com.example.aorms_seda;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

public class hallManagerOrderListAdapter extends FirestoreRecyclerAdapter<hall_manager_new_order_list_item,hallManagerOrderListAdapter.OrderListViewHolder > {
    private OnItemClickListener mListener;
    public hallManagerOrderListAdapter(FirestoreRecyclerOptions<hall_manager_new_order_list_item> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(OrderListViewHolder orderListViewHolder, int i, hall_manager_new_order_list_item hall_manager_new_order_list_item) {
        orderListViewHolder.orderStatusTextView.setText("");
        orderListViewHolder.orderIdTextView.setText("Table "+String.valueOf(hall_manager_new_order_list_item.getTable()));
        orderListViewHolder.orderTimeTextView.setText("Time "+String.valueOf(hall_manager_new_order_list_item.getTime()));


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
        Button editorder;
        Button deleteorder;
        public ImageView imageView;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.orderIdTextView = itemView.findViewById(R.id.order_id);
            this.orderStatusTextView= itemView.findViewById(R.id.order_status);
            this.orderTimeTextView=itemView.findViewById(R.id.time_status);
            editorder=itemView.findViewById(R.id.button5);
            this.deleteorder=itemView.findViewById(R.id.button7);
            imageView = itemView.findViewById(R.id.image_vi);
            deleteorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && mListener != null) {
                            mListener.ondeleteClick(getSnapshots().getSnapshot(position),position);
                        }
                    }


                }
            });
            editorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && mListener != null) {
                            mListener.onbuttonClick(getSnapshots().getSnapshot(position),position);
                        }
                    }

                }
            });

        }


    }
    public interface OnItemClickListener {
        void ondeleteClick(DocumentSnapshot documentSnapshot,int position);
        void onbuttonClick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
