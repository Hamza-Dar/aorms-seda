package com.example.aorms_seda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cartOrderPlacement extends AppCompatActivity {

    List<OrderItem> lst_food;
    TextView no_queue;
    OrderItemAdapter myAdapter;
    QueryDocumentSnapshot foodItem;
    Context mContext;
    Button confirm_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_order_placement);
        no_queue=findViewById(R.id.queue_empty);
        mContext= getApplicationContext();



        confirm_btn= findViewById(R.id.confirmCartOrder);
        lst_food = OrderedItemsQueue.Singleton().getOrderItemList();
        if(!lst_food.isEmpty()) {
            Log.e("HIDING", "onCreate: ");
            no_queue.setVisibility(View.INVISIBLE);
            confirm_btn.setVisibility(View.VISIBLE);

            initializeRcv();
        }


        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(mContext, cartOrderPlacement.class);
                // start the activity
                //mContext.startActivity(intent);
                Map<String, Object> newOrder = new HashMap<>();
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
                OrderedItemsQueue queue = OrderedItemsQueue.Singleton();
                int max = -1;
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                for(OrderItem temp : queue.orderItemList){
                    Map<String, Object> tempMap = new HashMap<>();
                    tempMap.put("foodItem", db.document("Fooditem/" + temp.item.id));
                    tempMap.put("itemStatus", "waiting");
                    tempMap.put("itemName", temp.item.name);
                    listOfMaps.add(tempMap);
                    max = Math.max(temp.item.time, max);
                }

                newOrder.put("Items", listOfMaps);
                newOrder.put("Status", "waiting");
                newOrder.put("Table", -1);
                newOrder.put("Time", max);
                newOrder.put("Priority", "normal");

                String id = db.collection("Orders").document().getId();
                if (queue.orderId!=null){
                    id = queue.orderId;
                }
                else{
                    queue.orderId = id;
                }
                //queue.orderId = id;
                db.collection("Orders").document(id).set(newOrder).addOnSuccessListener(new OnSuccessListener < Void > () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String a = "";
                    }
                });


            }
        });


    }


    protected void initializeRcv() {

        RecyclerView myrv = findViewById(R.id.cartOrderScreen_rcv);

        myAdapter = new OrderItemAdapter(this, lst_food);

        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);

    }

}
