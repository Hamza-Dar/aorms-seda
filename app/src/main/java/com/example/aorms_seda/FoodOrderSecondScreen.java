package com.example.aorms_seda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodOrderSecondScreen extends AppCompatActivity {

        private TextView tvtitle,tvdescription,tvcategory;
        private TextView img_txt;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_food_order_second_screen);

            img_txt= findViewById(R.id.food1);
            img_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FoodOrderSecondScreen.this, ItemActivity.class);
                    startActivity(intent);
                }
            });

        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");


        }


    }
