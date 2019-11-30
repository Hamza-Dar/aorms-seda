package com.example.aorms_seda;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        //startActivity(new Intent(this, MainActivity.class));
        finish();
        return true;
    }

    public void TabletButton(View view){
        Intent i = new Intent(this, TabletOption.class);
        startActivity(i);
        finish();
    }

    public void MenuButton(View view){
        Intent i = new Intent(this, MenuOption.class);
        startActivity(i);
        finish();
    }

    public void EmployeeButton(View view){
        Intent i = new Intent(this, EmployeeOption.class);
        startActivity(i);
        finish();
    }
}
