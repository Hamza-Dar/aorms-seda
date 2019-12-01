package com.example.aorms_seda;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.gauge.pointers.Bar;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class MonthlyReports extends Fragment {

    public MonthlyReports() {
        // Required empty public constructor
    }

    Cartesian br;
    HashMap<Long,Long> bill;
    List<DataEntry> data;
    View view;
    Spinner sp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_monthly_reports, container, false);
        sp = view.findViewById(R.id.MonthReports);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int i11 = sp.getSelectedItemPosition();
                setchart(i11+1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }
    TextView txt;
    Long total = 0L;

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;

    private void setchart(int month){

        total = 0L;
//        br = AnyChart.column();
        bill = new HashMap<>();
        FirebaseFirestore.getInstance().collection("Bills")
                .whereEqualTo("Month",month).whereEqualTo("Year", Calendar.getInstance().get(Calendar.YEAR))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    List<DocumentSnapshot> docs = task.getResult().getDocuments();
                    txt = view.findViewById(R.id.NumOrderMonthly);
                    txt.setText(String.valueOf(docs.size()));
                    barChart = view.findViewById(R.id.any_chart_view);
                     //anyChartView.clear();
                    for(DocumentSnapshot d:docs){
                        Long x = bill.get(d.getLong("Day"));
                        total += d.getLong("total");
                        if(x==null)
                            x = 0L;
                        x = x+d.getLong("total");
                        Long y = d.getLong("Day");
                        if(y!=null)
                            bill.put(y, x );
                    }
                    barEntries = new ArrayList<>();
                    for (long i=1L; i<=30; i++) {

                        if(bill.get(i)==null) {
                            barEntries.add(new BarEntry(i, 0));
                        }
                        else {
                            Float l =  Float.valueOf(bill.get(i));
                            barEntries.add(new BarEntry(i, l));
                        }
                    }
                    barDataSet = new BarDataSet(barEntries, "");
                    barData = new BarData(barDataSet);
                    barChart.setData(barData);
                    txt = view.findViewById(R.id.MonthlySales);
                    txt.setText(String.valueOf(total));

//                    anyChartView.setChart(br);
//                    br.autoRedraw();
////                    anyChartView.notify();
//
//                    anyChartView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            br.fullScreen();
//                        }
//                    });
                }
            }
        });
    }

}
