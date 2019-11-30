package com.example.aorms_seda;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_option);

        FoodItem i1 = new FoodItem("Stuffed Chicken", "0001", "Yes", 1000, 15);
        FoodItem i2 = new FoodItem("Beef Steak", "0002", "Yes", 1200, 20);
        FoodItem i3 = new FoodItem("Chicken Handi", "0003", "Yes", 800, 15);
        FoodItem i4 = new FoodItem("Beef Burger", "0004", "No", 600, 15);
        FoodItem i5 = new FoodItem("Curly Fries", "0005", "Yes", 300, 10);

        ArrayList<FoodItem> items = new ArrayList<>();
        items.add(i1);
        items.add(i2);
        items.add(i3);
        items.add(i4);
        items.add(i5);

        RecyclerView recyclerView = findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(new MenuAdapter(items));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        //startActivity(new Intent(this, AdminOptions.class));
        finish();
        return true;
    }

}
