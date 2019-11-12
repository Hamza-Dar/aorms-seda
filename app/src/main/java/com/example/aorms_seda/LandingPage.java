package com.example.aorms_seda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
    }

    public void openReports(View v){

        Intent i = new Intent(this, ReportsActivity.class);
        startActivity(i);
    }

    public void openOrders(View v){

        Intent i = new Intent(this, FoodOrderMainMenu.class);
        startActivity(i);
    }

}
