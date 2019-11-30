package com.example.aorms_seda;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_linear extends Fragment {
    private RecyclerView myOrderList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("Alert");
    private HallAlertAdapter orderAdapter;


    public fragment_linear() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Toast.makeText(getActivity(), "Linear", Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_linear, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Query query = orderRef.orderBy("Time",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<hall_alert> options = new FirestoreRecyclerOptions.Builder<hall_alert>()
                .setQuery(query,hall_alert.class)
                .build();

        orderAdapter = new HallAlertAdapter(options);
        myOrderList =  (RecyclerView)view.findViewById(R.id.alert);
        myOrderList.setHasFixedSize(true);
        myOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        myOrderList.setAdapter(orderAdapter);
    }
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