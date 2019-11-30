package com.example.aorms_seda;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class      Restaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
    }
    public void place(View v)
    {
        Intent i = new Intent(this, menuaroms.class);
        startActivity(i);
    }
}
