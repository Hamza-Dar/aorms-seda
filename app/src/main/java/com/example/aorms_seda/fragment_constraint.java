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
public class fragment_constraint extends Fragment {

    private View orderList;
    private RecyclerView myOrderList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("Orders");

    private hallManagerOrderListAdapter orderAdapter;
    public fragment_constraint() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        orderList = inflater.inflate(R.layout.fragment_constraint, container, false);
        return orderList;

    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Toast.makeText(getActivity(), "COnstraint", Toast.LENGTH_LONG).show();
        Query query = orderRef.orderBy("Table",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<hall_manager_new_order_list_item> options = new FirestoreRecyclerOptions.Builder<hall_manager_new_order_list_item>()
                .setQuery(query,hall_manager_new_order_list_item.class)
                .build();

        orderAdapter = new hallManagerOrderListAdapter(options);

        myOrderList = (RecyclerView)view.findViewById(R.id.order_list_hall_manager);
        myOrderList.setHasFixedSize(true);
        myOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        myOrderList.setAdapter(orderAdapter);
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
