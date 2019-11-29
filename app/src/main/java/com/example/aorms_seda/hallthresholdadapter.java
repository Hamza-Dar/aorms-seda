package com.example.aorms_seda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

public class hallthresholdadapter extends FirestoreRecyclerAdapter<hall_threshold,hallthresholdadapter.OrderListViewHolder > {
    private OnItemClickListener mListener;
    public hallthresholdadapter(FirestoreRecyclerOptions<hall_threshold> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(OrderListViewHolder orderListViewHolder, int i, hall_threshold hall_manager_new_order_list_item) {
        orderListViewHolder.orderIdTextView.setText("Threshold");
        orderListViewHolder.orderNameTextView.setText(hall_manager_new_order_list_item.getName());
        orderListViewHolder.orderThresholdTextView.setText(String.valueOf(hall_manager_new_order_list_item.getThreshold())+"Kg");


    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hall__threshold,
                parent,false);
        return new OrderListViewHolder(v);
    }

    class OrderListViewHolder extends RecyclerView.ViewHolder{
        TextView orderIdTextView;
        TextView orderNameTextView;
        TextView orderThresholdTextView;
        Button editorder;
        public ImageView minusimageView;
        public ImageView plusimageView;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.orderIdTextView = itemView.findViewById(R.id.ingredientid);
            this.orderNameTextView= itemView.findViewById(R.id.ingredientName);
            this.orderThresholdTextView=itemView.findViewById(R.id.idProductQty);
            //this.minusimageView=itemView.findViewById(R.id.idMinusICon);
            //this.plusimageView=itemView.findViewById(R.id.idPlusIcon);

            editorder=itemView.findViewById(R.id.button);
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
        void onbuttonClick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
