package com.example.aorms_seda;

import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InventoryManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_manager);

        Ingredient i1 = new Ingredient("Chicken", "0001", 20, 15);
        Ingredient i2 = new Ingredient("Beef", "0002", 18, 15);
        Ingredient i3 = new Ingredient("Olives", "0003", 16, 15);
        Ingredient i4 = new Ingredient("Cheese", "0004", 21, 15);
        Ingredient i5 = new Ingredient("Buns", "0005", 22, 15);

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(i1);
        ingredients.add(i2);
        ingredients.add(i3);
        ingredients.add(i4);
        ingredients.add(i5);

        RecyclerView recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(new IngredientAdapter(ingredients));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        //startActivity(new Intent(this, MainActivity.class));
        finish();
        return true;
    }
}
