package com.example.aorms_seda;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    RecyclerView dishes;
    ArrayList<Dish> dishList;
    GestureDetector dishDetector;
    DishAdapter dishAdapter;
    Order orderInfo;
    TextView ordertxt;
    TextView statustxt;
    TextView orderStatus;
    Spinner dropdown;
    Button updateBtn;
    Button doneBtn;
    Button cancelBtn;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen_activity_order_details);
        db=FirebaseFirestore.getInstance();
        orderInfo= (Order)getIntent().getSerializableExtra("Order");
        dishes = (RecyclerView) findViewById(R.id.dishesrcv);
        dishList=orderInfo.getDishes();
        dropdown = findViewById(R.id.statusSpinner);
        orderStatus=findViewById(R.id.orderStatustxtview);
        updateBtn=findViewById(R.id.proceedbtn);
        doneBtn=findViewById(R.id.doneOrderbtn);
        doneBtn.setVisibility(View.GONE);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderStatus.setText(dropdown.getSelectedItem().toString());
                db.collection("Orders").document(orderInfo.getOrderId()).update("Status",dropdown.getSelectedItem().toString());
            }
        });


        String[] items = new String[]{"waiting", "progress", "ready"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        ordertxt=findViewById(R.id.orderIdtxtview);
        statustxt=findViewById(R.id.serveTimetxtview);
        ordertxt.setText(String.valueOf(orderInfo.getOrderId()));
        statustxt.setText(String.valueOf(orderInfo.getServeTime()));
        orderStatus.setText(orderInfo.status);
        cancelBtn=findViewById(R.id.cancelbtn);
        cancelBtn.setVisibility(View.GONE);
        setdishes();

    }

    public void setdishes()
    {


        dishDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener()
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
                }
                return true;
            }
        }

        );


        dishAdapter=new DishAdapter(dishList,R.layout.kitchen_dish_holder,1);
        dishes.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));
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
        dishes.setItemAnimator(new DefaultItemAnimator());
        dishes.setAdapter(dishAdapter);
    }


}
