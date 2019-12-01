package com.example.aorms_seda;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FoodOrderMainMenu extends AppCompatActivity {

    List<Food> lst_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_main_menu);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        lst_food = new ArrayList<>();
        OrderedItemsQueue.tableNum = getIntent().getIntExtra("TableNumber", -1);
        final Map<String, String> types = new HashMap<String,String>();
        firestore.collection("Fooditem").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //lst_food.add(new Food(document.get("Type").toString(), document.get("Type").toString(), document.get("Type").toString(), R.drawable.fast));
                        String title = document.get("Type").toString();
                        if (!types.containsKey(title)) {
                            types.put(title, title);
                            lst_food.add(new Food(title, title, title, R.drawable.fast));
                        }
                    }
                    initializeRcv();
                }
            }
        });
    }

    protected void initializeRcv() {
        RecyclerView myrv = (RecyclerView) findViewById(R.id.FoodOrderMainMenu_rcv);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lst_food);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }

}
