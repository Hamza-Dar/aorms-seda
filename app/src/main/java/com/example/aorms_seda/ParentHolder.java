package com.example.aorms_seda;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ParentHolder extends RecyclerView.ViewHolder {


    TextView title;

    GestureDetector dishDetector;

    ArrayList<Dish> dishList;
    DishAdapter dishAdapter;
    RecyclerView dishes;

    public ParentHolder(@NonNull View itemView) {
        super(itemView);
        dishes= itemView.findViewById(R.id.parentRV);
        title= itemView.findViewById(R.id.QueueTitle);


    }

    public void setValues(final Context context, String name, ArrayList<Dish> data)
    {

        title.setText(name);
        dishList=data;


        dishDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //      Toast.makeText(c,"onSingleTap",Toast.LENGTH_SHORT).show();

                int index=0;
                View child = dishes.findChildViewUnder(e.getX(), e.getY());
                if(child != null)
                {

                    //index= dishes.getChildAdapterPosition(child);
                    //open movie activity to show details;
                    //Intent i=new Intent(getApplicationContext(),DishDetails.class);
                    //i.putExtra("DishInfo",  dishList.get(index));
                    //startActivity(i);
                    Toast.makeText(context,"Check", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        }

        );



        dishAdapter=new DishAdapter(dishList,R.layout.kitchen_dish_holder,0);
        dishes.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        dishes.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                dishDetector.onTouchEvent(motionEvent);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
        dishes.setHasFixedSize(true);
        dishes.setItemAnimator(new DefaultItemAnimator());

        dishes.setAdapter(dishAdapter);
    }


}
