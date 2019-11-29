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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_monthly_reports, container, false);

        Cartesian br = AnyChart.bar();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("John", 10000));
        data.add(new ValueDataEntry("Jake", 12000));
        data.add(new ValueDataEntry("Peter", 18000));

        br.data(data);

        AnyChartView anyChartView = (AnyChartView) view.findViewById(R.id.any_chart_view);
        anyChartView.setChart(br);

        return view;
    }


}
