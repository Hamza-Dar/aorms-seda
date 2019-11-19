package com.example.aorms_seda;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

public class FoodOrderMainMenu extends AppCompatActivity {

    List<Food> lst_food ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_main_menu);

        lst_food= new ArrayList<>();
        lst_food.add(new Food("Fast Food","Fast Food","Burgers, Pizza",R.drawable.fast));
        lst_food.add(new Food("Mexican Food","Spicy Food","Carne Adobada, Tostadas",R.drawable.mex));
        lst_food.add(new Food("Chinese Food","Chinese","Chow Mein, Kung Pao Chicken",R.drawable.chinese));
        lst_food.add(new Food("Desi Food","Desi Special","Pulao, Biryani, Quorma, Chicken Roast, Kabab",R.drawable.desi));
        lst_food.add(new Food("Italian Food","Italian Dishes","Alfredo, Puticino",R.drawable.italian));
        lst_food.add(new Food("Others","Snacks","French Fries, Cold Drinks",R.drawable.logo));
        lst_food.add(new Food("Fast Food","Fast Food","Burgers, Pizza",R.drawable.fast));
        lst_food.add(new Food("Mexican Food","Spicy Food","Carne Adobada, Tostadas",R.drawable.mex));


        RecyclerView myrv = (RecyclerView) findViewById(R.id.FoodOrderMainMenu_rcv);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lst_food);
        myrv.setLayoutManager(new GridLayoutManager(this,2));
        myrv.setAdapter(myAdapter);


    }
}
