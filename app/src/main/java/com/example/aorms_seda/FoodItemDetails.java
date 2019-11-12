package com.example.aorms_seda;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodItemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_details);

        FoodItem f =  (FoodItem) getIntent().getSerializableExtra("FoodItem");
        TextView textView = findViewById(R.id.itemTitle);
        textView.setText("FoodItem Details: " + f.name);
    }
}
