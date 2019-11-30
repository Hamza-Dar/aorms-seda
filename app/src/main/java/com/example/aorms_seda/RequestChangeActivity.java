package com.example.aorms_seda;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestChangeActivity extends AppCompatActivity {

    RecyclerView dishes;
    ArrayList<Dish> dishListInner;
    ArrayList<RequestsChange> dishList;
    GestureDetector dishDetector;
    ChangeDishAdapter dishAdapter;
    Order orderInfo;
    TextView ordertxt;
    TextView statustxt;
    TextView orderStatus;
    Spinner dropdown;
    Button Updatebtn;
    Button cancelbtn;
    Button doneOrderbtn;
    TextView dishesOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        dishes = (RecyclerView) findViewById(R.id.dishesrcv);

        dishListInner=new ArrayList<Dish>();
        dishListInner.add(new Dish('1',"Pasta","12:00","12","Waiting","Italian"));
        dishListInner.add(new Dish('1',"Pizza","12:00","12","Waiting","Italian "));

        orderInfo=new Order(123,"50",dishListInner,"InProgress");

        ordertxt=findViewById(R.id.orderIdtxtview);
        statustxt=findViewById(R.id.serveTimetxtview);
        orderStatus=findViewById(R.id.orderStatustxtview);

        ordertxt.setText(String.valueOf(orderInfo.getOrderId()));
        statustxt.setText(orderInfo.getServeTime());

        dishList=new ArrayList<RequestsChange>();

        dishList.add(new RequestsChange(orderInfo.dishes.get(0),"delete","pending",orderInfo.getOrderId()));
        dishList.add(new RequestsChange(orderInfo.dishes.get(1),"delete","pending",orderInfo.getOrderId()));

        orderStatus.setText(orderInfo.getStatus());

        dropdown = findViewById(R.id.statusSpinner);

        Updatebtn=findViewById(R.id.proceedbtn);
        doneOrderbtn=findViewById(R.id.doneOrderbtn);

        dropdown.setVisibility(View.GONE);
        cancelbtn=findViewById(R.id.cancelbtn);
        cancelbtn.setVisibility(View.VISIBLE);
        Updatebtn.setVisibility(View.GONE);
        doneOrderbtn.setVisibility(View.GONE);


        dishesOrder=findViewById(R.id.dishesOrdertxtview);
        dishesOrder.setText("Requests");

        dishList=null;


        if(dishList==null || dishList.isEmpty())
        {

            dishesOrder.setVisibility(View.GONE);
            dishes.setVisibility(View.GONE);
            cancelbtn.setText("Cancel Order");
            doneOrderbtn.setVisibility(View.VISIBLE);

            //cancelbtn order status in firebase
        }
        else
        {
            cancelbtn.setText("DONE");
            setdishes();

        }

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


        dishAdapter=new ChangeDishAdapter(dishList,R.layout.change_dish_holder);
        dishes.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
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
