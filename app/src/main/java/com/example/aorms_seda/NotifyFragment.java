package com.example.aorms_seda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NotifyFragment extends Fragment {


    RecyclerView orders;
    GestureDetector orderDetector;
    OrderAdapter orderAdapter;
    ArrayList<Order> orderList;
    ArrayList<Dish> dishes;
    ArrayList<Order>ordersFb;
    Order simpleOrder;


    private FirebaseFirestore db;

    private kitchenActivity kitchenActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.notify,container,false);

        //load orders here;

        db= FirebaseFirestore.getInstance();
        CollectionReference dbOrders=db.collection("Orders");
        ordersFb=new ArrayList<>();
        simpleOrder=new Order(0,null,null,null);

        //load chefs and their specialities

        dbOrders.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {


                    simpleOrder.setOrderId(0);   //get orderid
                  //  simpleOrder.setStatus((String) documentSnapshot.getData().get("Status"));
                   //  simpleOrder.setServeTime((String) documentSnapshot.getData().get("Time"));
                    ArrayList<String>dishes= (ArrayList<String>) documentSnapshot.getData().get("Items");
                //    Log.i("TAG",dishes.get(0));

                   /* Map<String, Object> map = (Map<String, Object>) documentSnapshot.getData().get("Items");
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        if (entry.getKey().equals("Items")) {     //get all dishes
                            Log.d("TAG", entry.getValue().toString());
                        }

                    }*/
                }
            }
        });

        orders = (RecyclerView) root.findViewById(R.id.ordersrcv);

        kitchenActivity = (kitchenActivity) getActivity();

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
                    Intent i=new Intent(kitchenActivity, OrderDetailsActivity.class);
                    i.putExtra("Order", orderList.get(index));
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
