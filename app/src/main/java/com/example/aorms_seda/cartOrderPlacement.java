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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.Calendar;
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
        no_queue = findViewById(R.id.queue_empty);
        mContext = getApplicationContext();


        confirm_btn = findViewById(R.id.confirmCartOrder);
        lst_food = OrderedItemsQueue.Singleton().getOrderItemList();
        if (!lst_food.isEmpty()) {
            Log.e("HIDING", "onCreate: ");
            no_queue.setVisibility(View.INVISIBLE);
            confirm_btn.setVisibility(View.VISIBLE);

            initializeRcv();
        }


        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final OrderedItemsQueue queue = OrderedItemsQueue.Singleton();
                if (queue.orderItemList.size() > 0) {
                    final List<OrderItem> items = queue.getOrderItemList();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    if (queue.orderId != null) {
                        db.collection("Orders").document(queue.orderId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap> items1 = (List<HashMap>) documentSnapshot.get("Items");
                                queue.priority = documentSnapshot.get("Priority").toString();
                                queue.stat = documentSnapshot.get("Status").toString();
                                for (HashMap h : items1) {
                                    for (OrderItem i : queue.orderItemList) {
                                        if (i.getName().equals(h.get("itemName"))) {
                                            i.status = h.get("itemStatus").toString();
                                        }
                                    }
                                }
                                addToDb();
                            }
                        });
                    } else {
                        addToDb();
                    }
                }

            }
        });


    }


    protected void initializeRcv() {

        RecyclerView myrv = findViewById(R.id.cartOrderScreen_rcv);

        myAdapter = new OrderItemAdapter(this, lst_food);

        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);

    }
    Map<String, Object> newOrder = new HashMap<>();
    Map<String, Object> newBill = new HashMap<>();
    String oid;
    protected void addToDb() {

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
        OrderedItemsQueue queue = OrderedItemsQueue.Singleton();
        int max = -1;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int total = 0;
        for (OrderItem temp : queue.orderItemList) {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("foodItem", "Fooditem/" + temp.item.id);
            tempMap.put("itemStatus", temp.status);
            tempMap.put("itemName", temp.item.name);
            listOfMaps.add(tempMap);
            total += temp.item.price;
            max = Math.max(temp.item.time, max);
        }

        String p = "";
        String s = "";
        if (queue.stat != null) {
            s = queue.stat;
        } else {
            s = "waiting";
        }
        if (queue.priority != null) {
            p = queue.priority;
        } else {
            p = "normal";
        }
        newOrder.put("Items", listOfMaps);
        newOrder.put("Status", s);
        newOrder.put("Table", OrderedItemsQueue.tableNum);
        newOrder.put("Time", max);
        newOrder.put("Priority", p);

        String id = db.collection("Orders").document().getId();
        oid= db.collection("Bills").document().getId();
        if (queue.orderId != null) {
            id = queue.orderId;
            oid = queue.billId;
        } else {
            queue.orderId = id;
            queue.billId = oid;
        }

        newBill.put("Order", id);
        newBill.put("total", total);
        String date = Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR);
        newBill.put("date", date);
        newBill.put("Month", (Calendar.getInstance().get(Calendar.MONTH)+1));
        newBill.put("Year", Calendar.getInstance().get(Calendar.YEAR));
        newBill.put("Day", Calendar.getInstance().get(Calendar.DATE));
        Long DDD = (long) ((Calendar.getInstance().get(Calendar.YEAR)) * 365 +
                (Calendar.getInstance().get(Calendar.MONTH)+1) * 30
                + Calendar.getInstance().get(Calendar.DATE));
        newBill.put("DateN", DDD);
        //queue.orderId = id;
        db.collection("Orders").document(id).set(newOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Order placed successfully", Toast.LENGTH_LONG).show();
                OrderedItemsQueue.emptycart();
                FirebaseFirestore.getInstance().collection("Bills").document(oid).set(newBill).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                });
            }
        });

    }

}
