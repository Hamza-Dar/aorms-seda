package com.example.aorms_seda;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
