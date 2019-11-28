package com.example.aorms_seda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        FirebaseApp.initializeApp(this);
    }

    public void openReports(View v){

        Intent i = new Intent(this, ReportsActivity.class);
        startActivity(i);
    }

    public void openOrders(View v){

        Intent i = new Intent(this, FoodOrderMainMenu.class);
        startActivity(i);
    }
    public void openAdmin(View v){

        Intent i = new Intent(this, MenuOption.class);
        startActivity(i);
    }

    public void openInventory(View v){

        Intent i = new Intent(this, InventoryManager.class);
        startActivity(i);
    }

    public void openKitchen(View v){

        Intent i = new Intent(this, kitchenActivity.class);
        startActivity(i);
    }

    public void openHallManager(View v){

        Intent i = new Intent(this, activity_SignUp.class);
        startActivity(i);
    }

}
