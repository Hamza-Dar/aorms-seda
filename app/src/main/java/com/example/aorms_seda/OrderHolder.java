package com.example.aorms_seda;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class
OrderHolder extends   RecyclerView.ViewHolder {

    TextView orderIDtxt;
    TextView orderStatustxt;


    public OrderHolder(View itemView) {
        super(itemView);
        orderIDtxt=itemView.findViewById(R.id.OrderIDtxtview);
        orderStatustxt=itemView.findViewById(R.id.OrderStatustxtview);

    }

    public void setValues(Order OrderInfo)
    {
        orderIDtxt.setText(String.valueOf(OrderInfo.getOrderId()));
        orderStatustxt.setText(OrderInfo.getStatus());


    }
}
