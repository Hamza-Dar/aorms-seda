package com.example.aorms_seda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class cartaroms extends AppCompatActivity {
    Context context;
    RecyclerView recyclerView;
    AdapterForCart adapter;
    CartItems MyCart=CartItems.get_Instance();
    ///DatabaseReference myDatabaseReference;
    private FirebaseAuth mAuth;
    Button proceed;
    Spinner spinner;
    public ArrayList<String> tables = new ArrayList<>();

    final String[] arr = new String[] { "1", "2",
            "3", "4", "5"};
    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cartaroms);
        try {
            context = getApplicationContext();
            proceed =findViewById(R.id.buttonplace);
            recyclerView = (RecyclerView)findViewById(R.id.recyclerC);
            settingTheRecyclerView();
            spinner = (Spinner) findViewById(R.id.spinner2);
            getTables();
            ArrayAdapter<String> adaptr = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arr);
            adaptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adaptr);

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

        }

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrderFireBase();
            }
        });

    }
    public void getTables()
    {
        FirebaseFirestore.getInstance().collection("Tablet")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document :
                            task.getResult()) {
                        String id = document.get("Table").toString();
                        tables.add(id);
                    }

                }
            }
        });
            FirebaseFirestore.getInstance().collection("Fooditem")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document :
                            task.getResult()) {
                        String name = (document.get("Name").toString());
                        String id=document.getId().toString();
                        MyCart.ids.add(new FoodId(id,name));
                    }

                }
            }
        });
    }

    private void placeOrderFireBase() {
        Map<String, Object> newOrder = new HashMap<>();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DataListForCart temp;
        for(int i=0;i<MyCart.cartItems.size();i++){
            temp=MyCart.cartItems.get(i);
            String id=MyCart.getNID(temp.getItemName());
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("foodItem", "Fooditem/"+id);//(MyCart.ids.get(i)));
            tempMap.put("itemStatus", "waiting");
            tempMap.put("itemName", temp.getItemName());
            listOfMaps.add(tempMap);
        }

        newOrder.put("Items", listOfMaps);
        newOrder.put("Status", "Waiting");
        String text = spinner.getSelectedItem().toString();
        int t=Integer.valueOf(text);//valueOf(text);
        newOrder.put("Table", t);
        newOrder.put("Time", 0);
        newOrder.put("Priority", "High");

        //String id = db.collection("Orders").document().getId();
        //queue.orderId = id;
        db.collection("Orders")
                .add(newOrder)//document(id).set(newOrder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Order FAILED", Toast.LENGTH_SHORT).show();
                    }
                });
        MyCart.EmptyCart();
        this.finish();
    }
    private void settingTheRecyclerView() {
        adapter = new AdapterForCart(MyCart.getCartItems(), context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
    public void back2(View v)
    {
        Intent i = new Intent(this,menuaroms.class);
        this.finish();
    }
}


