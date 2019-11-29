package com.example.aorms_seda;

import android.content.Intent;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class fragment_assign_tablet  extends Fragment{
    private View tabletList;
    private RecyclerView myTabletList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("Tablet");
    private HallManagerTableListAdapter tabletAdapter;

    public fragment_assign_tablet() {
        //Required Empty constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Toast.makeText(getActivity(), "Linear", Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_assign_tablet, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Query query = orderRef.orderBy("RegNo",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<hall_manager_tablet_model> options = new FirestoreRecyclerOptions.Builder<hall_manager_tablet_model>()
                .setQuery(query,hall_manager_tablet_model.class)
                .build();
        tabletAdapter = new HallManagerTableListAdapter(options);
        myTabletList = (RecyclerView)view.findViewById(R.id.show_tablets);
        myTabletList.setHasFixedSize(true);
        myTabletList.setLayoutManager(new LinearLayoutManager(getContext()));
        myTabletList.setAdapter(tabletAdapter);
        tabletAdapter.setOnItemClickListener(new HallManagerTableListAdapter.OnItemClickListener() {
            @Override
            public void onassignClick(DocumentSnapshot documentSnapshot, int position) {
                ArrayList<String> object=new ArrayList<>();
                String id = documentSnapshot.getId();
                String regNo= documentSnapshot.get("RegNo").toString();
                String table= documentSnapshot.get("Table").toString();
                String model= documentSnapshot.get("Model").toString();
                object.add(id);
                object.add(regNo);
                object.add(table);
                object.add(model);
                Intent intent = new Intent(getActivity(), assignTableToTabletActivity.class);
                intent.putStringArrayListExtra("message", object);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        tabletAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        tabletAdapter.stopListening();
    }
}
