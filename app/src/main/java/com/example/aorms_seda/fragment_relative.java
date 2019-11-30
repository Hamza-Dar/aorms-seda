package com.example.aorms_seda;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_relative extends Fragment{
    private View orderList;
    private RecyclerView myOrderList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("Orders");
    private hallManagerOrderListAdapter orderAdapter;
    List<String>items = new ArrayList<>();
    public fragment_relative() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // Toast.makeText(getActivity(),"Relative",Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_relative, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Toast.makeText(getActivity(), "COnstraint", Toast.LENGTH_LONG).show();
        Query query = orderRef.orderBy("Table",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<hall_manager_new_order_list_item> options = new FirestoreRecyclerOptions.Builder<hall_manager_new_order_list_item>()
                .setQuery(query,hall_manager_new_order_list_item.class)
                .build();

        orderAdapter = new hallManagerOrderListAdapter(options);
        myOrderList = (RecyclerView)view.findViewById(R.id.new_order_list);
        myOrderList.setHasFixedSize(true);
        myOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        myOrderList.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new hallManagerOrderListAdapter.OnItemClickListener() {
            @Override
            public void ondeleteClick(DocumentSnapshot documentSnapshot, int position) {
                ArrayList<String>object=new ArrayList<>();
                String id = documentSnapshot.getId();
                String status = documentSnapshot.get("Status").toString();
                String table = documentSnapshot.get("Table").toString();
                String time = documentSnapshot.get("Time").toString();
                String Priority = documentSnapshot.get("Priority").toString();
                object.add(id);
                object.add(status);
                object.add(table);
                object.add(time);
                object.add(Priority);
                Log.d("moiz","Heelo"+object.size());
                Intent intent = new Intent(getActivity(), hall_manager_orderdetail.class);
                intent.putStringArrayListExtra("message", object);
                startActivity(intent);
            }

            @Override
            public void onbuttonClick(DocumentSnapshot documentSnapshot, int position) {

                String id = documentSnapshot.getId();
                Intent intent = new Intent(getActivity(), hall_manager_items_detail.class);
                intent.putExtra("message", id);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        orderAdapter.startListening();
        orderRef.addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    DocumentSnapshot documentSnapshot = dc.getDocument();
                    String id = documentSnapshot.getId();
                    int oldIndex = dc.getOldIndex();
                    int newIndex = dc.getNewIndex();
                    Log.d("Moiz","Mainnn");
                    Map<String, Object> waiter = new HashMap<>();
                    switch (dc.getType()) {
                        case ADDED:
                            waiter.put("Change", "Added");
                            waiter.put("Time", new Timestamp(new Date()));
                            waiter.put("Order",id);
                            db.collection("Alert").document(id)
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
                            Log.d("Moiz","Added");
                            break;
                       /* case MODIFIED:
                            waiter.put("Change", "MODIFIED");
                            waiter.put("Time", new Timestamp(new Date()));
                            waiter.put("Order",id);
                            db.collection("Alert").document(id)
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
                            Log.d("Moiz","Modified");
                            break;
                        case REMOVED:
                            waiter.put("Change", "REMOVED");
                            waiter.put("Time", new Timestamp(new Date()));
                            waiter.put("Order",id);
                            db.collection("Alert").document(id)
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
                            Log.d("Moiz","Removed");
                            break;*/
                    }
                }
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        orderAdapter.stopListening();
    }

}