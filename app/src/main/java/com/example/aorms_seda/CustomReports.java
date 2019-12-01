package com.example.aorms_seda;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomReports extends Fragment {

    int start,end;

    public CustomReports() {
        // Required empty public constructor
    }
    View v;
    final Calendar myCalendar = Calendar.getInstance();
    EditText DateStart, DateEnd;
    DatePickerDialog.OnDateSetListener Edate, Sdate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Sdate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                start = i*365 + (i1+1) * 30 + i2;
                updateLabel();
            }
        };

        Edate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                end = i*365 + (i1+1) * 30 + i2;
                updateLabel1();
            }
        };

        v = inflater.inflate(R.layout.fragment_custom_reports, container, false);

        DateEnd = v.findViewById(R.id.dateEnd);
        DateStart = v.findViewById(R.id.dateStart);

        DateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), Edate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        DateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), Sdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return v;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DateStart.setText(sdf.format(myCalendar.getTime()));
    }
    private ArrayList<Bill> bills;
    Long TotalBill;

    private void updateLabel1() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DateEnd.setText(sdf.format(myCalendar.getTime()));
        bills = new ArrayList<>();
        TotalBill = 0L;
        final BillAdapter adapter = new BillAdapter(bills, getContext());
        FirebaseFirestore.getInstance().collection("Bills")
                .whereGreaterThanOrEqualTo("DateN",start)
                .whereLessThanOrEqualTo("DateN", end)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult()!=null){
                            List<DocumentSnapshot> docs = task.getResult().getDocuments();
                            TextView t = v.findViewById(R.id.numbillCustom);
                            t.setText(String.valueOf(docs.size()));
                            for(DocumentSnapshot d:docs){
                                TotalBill += (Long) d.get("total");
                                bills.add(new Bill(Integer.valueOf(d.get("total").toString()),d.getString("Order"),d.getString("date")));
                                Log.i("Bill",d.getString("Order") );
                            }
                            adapter.notifyDataSetChanged();
                            t = v.findViewById(R.id.totalSaleCustom);
                            t.setText(String.valueOf(TotalBill));
                        }
                    }
                });
        RecyclerView rv = v.findViewById(R.id.rv_custonReport);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }
}