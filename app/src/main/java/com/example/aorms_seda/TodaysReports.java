package com.example.aorms_seda;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodaysReports extends Fragment {

    

    public TodaysReports() {
        // Required empty public constructor
    }
    private ArrayList<Bill> bills;
    View v;
    Long TotalBill;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v =  inflater.inflate(R.layout.fragment_todays_reports, container, false);
        bills = new ArrayList<>();
        TotalBill = 0L;
        final BillAdapter adapter = new BillAdapter(bills, getContext());
        final String date = Calendar.getInstance().get(Calendar.DATE)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR);
        FirebaseFirestore.getInstance().collection("Bills").whereEqualTo("date",date).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    List<DocumentSnapshot> docs =task.getResult().getDocuments();
                    TextView t = v.findViewById(R.id.numbillToday);
                    t.setText(String.valueOf(docs.size()));
                    for (DocumentSnapshot d:docs){
                        TotalBill += (Long) d.get("total");
                        bills.add(new Bill(Integer.valueOf(d.get("total").toString()),d.getString("Order"),date));
                        Log.i("Bill",d.getString("Order") );
                    }
                    adapter.notifyDataSetChanged();
                    t = v.findViewById(R.id.totalbillToday);
                    t.setText(String.valueOf(TotalBill));
                }
            }

        });

        RecyclerView rv = v.findViewById(R.id.rcv_today);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        return v;

    }

}