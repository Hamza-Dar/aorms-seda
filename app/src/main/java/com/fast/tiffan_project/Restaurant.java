package com.fast.tiffan_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Restaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void place()
    {
        Intent i = new Intent(this, menuaroms.class);
        startActivity(i);
    }
}
