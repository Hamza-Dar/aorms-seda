package com.example.aorms_seda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class hall_manager_items_detail extends AppCompatActivity {
    int count=0;
    private FirebaseFirestore db;
    private DocumentReference orderRef;
    private View orderList;
    private RecyclerView myOrderList;
    private FirebaseFirestore db1 = FirebaseFirestore.getInstance();
    private CollectionReference orderRef1 = db1.collection("Waiterfororder");
    public waiterhalladapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_manager_items_detail);
        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("message");
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final QueryDocumentSnapshot document1;


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int x= pref.getInt("key", 0);
        if (x==1){
           // Toast.makeText(this,"Preference",Toast.LENGTH_SHORT).show();

        }
        else {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("key", 1);
            editor.apply();
            Query capitalCities = db.collection("Employee").whereEqualTo("Job", "Waiter");
            // DocumentReference orderRef = db.document("Orders/"+message);
            db.collection("Employee")
                    .whereEqualTo("Job", "Waiter")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    count = 1;
                                    //obj.Name=document.getString("Name");
                                    //obj.id=document.getId();
                                    Log.d("sds", document.getId() + " => " + document.getData());
                                    Map<String, Object> waiter = new HashMap<>();
                                    waiter.put("name", document.getString("Name"));
                                    waiter.put("status", "Available");
                                    waiter.put("Count", "0");
                                    //waiter.put("Order", "");
                                    waiter.put("Orders", Arrays.asList());
                                    db.collection("Waiterfororder").document(document.getId())
                                            .set(waiter)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("Moiz", "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("sere", "Error writing document", e);
                                                }
                                            });
                                }
                            } else {
                                count = -1;
                                Log.d("aa", "Error getting documents: ", task.getException());

                            }
                        }
                    });
            // while (count == 0) {
            ///}
            //if (count == 1) {
        }
        Query query = orderRef1.whereEqualTo("status","Available");
        FirestoreRecyclerOptions<waiters_hall> options = new FirestoreRecyclerOptions.Builder<waiters_hall>()
                .setQuery(query,waiters_hall.class)
                .build();

        orderAdapter = new waiterhalladapter(options);
       // Toast.makeText(this,"Moiz",Toast.LENGTH_SHORT).show();
        myOrderList = (RecyclerView)findViewById(R.id.waiter_list);
        myOrderList.setHasFixedSize(true);
        myOrderList.setLayoutManager(new LinearLayoutManager(this));
        myOrderList.setAdapter(orderAdapter);

        orderAdapter.setOnItemClickListener(new waiterhalladapter.OnItemClickListener() {
            @Override
            public void assignwaiter(DocumentSnapshot documentSnapshot, int position) {
                String status="Available";
                String id = documentSnapshot.getId();
                String count = documentSnapshot.get("Count").toString();
                Log.d("Recycler", count);
                int counter=Integer.parseInt(count);
                counter++;
                if (counter==2)
                    status="Reserved";
                Map<String, Object> waiter = new HashMap<>();
                waiter.put("status", status);
                //waiter.put("Order", message);
                waiter.put("Count", String.valueOf(counter));
                DocumentReference washingtonRef = db.collection("Waiterfororder").document(id);
                washingtonRef.update("Orders", FieldValue.arrayUnion(message));
                db.collection("Waiterfororder").document(id)
                        .set(waiter,SetOptions.mergeFields("status","Count"))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                print();
                                Log.d("Moiz", "DocumentSnapshot successfully written!");
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("sere", "Error writing document", e);
                            }
                        });

            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        orderAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        orderAdapter.stopListening();
    }
    public void print(){
        Toast.makeText(this,"Waiter Assigned Successfully",Toast.LENGTH_SHORT).show();

    }


    }
