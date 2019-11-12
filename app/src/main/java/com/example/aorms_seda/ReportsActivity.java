package com.example.aorms_seda;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class ReportsActivity extends AppCompatActivity {

    SectionsPagerAdapter sectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sales Reports");
        setContentView(R.layout.activity_reports);
        sectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        sectionsPagerAdapter.addFragment(new TodaysReports(), "Today");
        sectionsPagerAdapter.addFragment(new MonthlyReports(), "Monthly");
        sectionsPagerAdapter.addFragment(new CustomReports(), "Custom");


        sectionsPagerAdapter.notifyDataSetChanged();
    }



}