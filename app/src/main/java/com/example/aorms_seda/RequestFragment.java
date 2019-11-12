package com.example.aorms_seda;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestFragment extends Fragment {

    RecyclerView orders;
    GestureDetector orderDetector;
    OrderAdapter orderAdapter;
    ArrayList<Order> orderList;
    ArrayList<Dish> dishes;

    private kitchenActivity kitchenActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.notify,container,false);

        orders = (RecyclerView) root.findViewById(R.id.ordersrcv);

        kitchenActivity = (kitchenActivity) getActivity();


        //load pending order requests from firebase;
        dishes=new ArrayList<Dish>();
        dishes.add(new Dish('1',"Pasta","12:00","12","Waiting","Italian"));
        dishes.add(new Dish('1',"Pizza","12:00","12","Waiting","Italian "));





        orderList=new ArrayList<Order>();
        orderList.add(new Order(123,"50",dishes,"InProgress"));
        orderList.add(new Order(124,"50",dishes,"InProgress"));

        setorders();

        return root;

    }

    public void setorders()
    {




        orderDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //      Toast.makeText(c,"onSingleTap",Toast.LENGTH_SHORT).show();

                int index=0;
                View child = orders.findChildViewUnder(e.getX(), e.getY());
                if(child != null)
                {

                    index= orders.getChildAdapterPosition(child);
                    //open movie activity to show details;
                    Intent i=new Intent(kitchenActivity, RequestChangeActivity.class);
                    startActivity(i);
                }
                return true;
            }
        }

        );




        orderAdapter = new OrderAdapter(orderList,R.layout.order_holder);
        orders.setLayoutManager(new GridLayoutManager(getContext(),2, GridLayoutManager.VERTICAL,false));
        //orders.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.GRID,false));
        orders.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                orderDetector.onTouchEvent(motionEvent);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
        orders.setItemAnimator(new DefaultItemAnimator());

        orders.setAdapter(orderAdapter);
    }

}
