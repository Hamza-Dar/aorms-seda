package com.example.aorms_seda;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TabletOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet_option);

        Tablet i1 = new Tablet("0001", "Samsung", "11/11/2019", 1);

        ArrayList<Tablet> items = new ArrayList<>();
        items.add(i1);

        RecyclerView recyclerView = findViewById(R.id.recycler3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(new TabletAdapter(items));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
