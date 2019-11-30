package com.example.aorms_seda;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen_activity_main);
    }

    void AdminButton(View view){
        startActivity(new Intent(this, AdminOptions.class));
        //finish();
    }

    void InventoryButton(View view){
        startActivity(new Intent(this, InventoryManager.class));
        //finish();
    }
}
