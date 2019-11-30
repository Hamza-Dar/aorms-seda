package com.example.aorms_seda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private ArrayList<Bill> arr;


    public BillAdapter(ArrayList<Bill> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.billrow,parent,false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        holder.setBill(arr.get(position));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    static class BillViewHolder extends RecyclerView.ViewHolder {
        View v;

        BillViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
        }

        void setBill(Bill b){
            TextView txt = v.findViewById(R.id.Order_name_bill);
            txt.setText("Order ID: "+b.OrderID);
            txt = v.findViewById(R.id.Price_bill);
            txt.setText("Total Bill: "+b.price);
            txt = v.findViewById(R.id.OrderDate_bill);
            txt.setText(b.Date);
        }
    }
}
