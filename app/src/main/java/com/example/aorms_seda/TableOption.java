package com.example.aorms_seda;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TableOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_option);

        Table i1 = new Table("0001", 1);

        ArrayList<Table> items = new ArrayList<>();
        items.add(i1);

        RecyclerView recyclerView = findViewById(R.id.recycler4);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(new TableAdapter(items));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
