package com.example.aorms_seda;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.gauge.pointers.Bar;

import java.util.ArrayList;
import java.util.List;


public class MonthlyReports extends Fragment {

    public MonthlyReports() {
        // Required empty public constructor
    }

    Cartesian br;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_monthly_reports, container, false);

        br = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        double i1 ;

        for (int i=1; i<=30; i++) {
            i1 = Math.random();
            i1 = i1*2000;
            data.add(new ValueDataEntry(i, i1));
        }

//        data.add(new ValueDataEntry("Week 2", 1000));
//        data.add(new ValueDataEntry("Week 3", 12000));
//        data.add(new ValueDataEntry("Week 4", 0));
//        data.add(new ValueDataEntry("Week 5", 1400));
//        data.add(new ValueDataEntry("Week 1", 1200));

        br.data(data);

        AnyChartView anyChartView = (AnyChartView) view.findViewById(R.id.any_chart_view);
        anyChartView.setChart(br);
        anyChartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.fullScreen();
            }
        });
        return view;
    }


}
