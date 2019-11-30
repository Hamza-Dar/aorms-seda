package com.example.aorms_seda;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_Frame extends Fragment {
    private FirebaseFirestore db;
    private DocumentReference orderRef;
    private View orderList;
    private RecyclerView myOrderList;
    private FirebaseFirestore db1 = FirebaseFirestore.getInstance();
    private CollectionReference orderRef1 = db1.collection("Ingredient");
    public hallthresholdadapter orderAdapter;


    public fragment_Frame() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Toast.makeText(getActivity(),"Frame",Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_frame, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Query query = orderRef1.orderBy("Threshold",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<hall_threshold> options = new FirestoreRecyclerOptions.Builder<hall_threshold>()
                .setQuery(query,hall_threshold.class)
                .build();

        orderAdapter = new hallthresholdadapter(options);
        myOrderList = (RecyclerView)view.findViewById(R.id.show_threshold);
        myOrderList.setHasFixedSize(true);
        myOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        myOrderList.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new hallthresholdadapter.OnItemClickListener() {
            @Override
            public void onbuttonClick(DocumentSnapshot documentSnapshot, int position) {
                String id =documentSnapshot.getId();
                ArrayList<String> object=new ArrayList<>();
                String threshold = documentSnapshot.get("Threshold").toString();
                object.add(id);
                object.add(threshold);
                Log.d("moiz","Heelo"+object.size());
                Intent intent = new Intent(getActivity(), hallthreshold.class);
                intent.putStringArrayListExtra("message", object);
                startActivity(intent);
                Log.d("moiz","Heelo"+documentSnapshot.getId());
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
}
