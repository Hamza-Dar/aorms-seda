package com.example.aorms_seda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class waiterhalladapter extends FirestoreRecyclerAdapter<waiters_hall,waiterhalladapter.OrderListViewHolder > {

    private waiterhalladapter.OnItemClickListener mListener;

    public waiterhalladapter(FirestoreRecyclerOptions<waiters_hall> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(waiterhalladapter.OrderListViewHolder orderListViewHolder, int i, waiters_hall waiters_hall) {
        String str = waiters_hall.getStatus();
        orderListViewHolder.waiterTextView.setText(waiters_hall.getName());
    }

    @NonNull
    @Override
    public waiterhalladapter.OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.waiter_hall_list,
                parent, false);
        return new waiterhalladapter.OrderListViewHolder(v);
    }

    class OrderListViewHolder extends RecyclerView.ViewHolder {
        TextView waiterTextView;
        Button assignorder;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.waiterTextView = itemView.findViewById(R.id.textView);
            assignorder = itemView.findViewById(R.id.button5);
            assignorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && mListener != null) {
                            mListener.assignwaiter(getSnapshots().getSnapshot(position),position);
                        }
                    }


                }
            });
        }

    }
public interface OnItemClickListener {
    void assignwaiter(DocumentSnapshot documentSnapshot, int position);
}

    public void setOnItemClickListener(waiterhalladapter.OnItemClickListener listener) {
        mListener = listener;
    }
}
