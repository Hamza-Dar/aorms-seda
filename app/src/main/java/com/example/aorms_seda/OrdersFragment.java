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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class OrdersFragment extends Fragment {


    RecyclerView orders;
    GestureDetector orderDetector;
    OrderAdapter orderAdapter;
    ArrayList<Order> orderList;

    private FirebaseFirestore db;

    private kitchenActivity kitchenActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.kitchen_orders_fragment,container,false);
        db= FirebaseFirestore.getInstance();
        orders = (RecyclerView) root.findViewById(R.id.ordersrcv);
        kitchenActivity = (kitchenActivity) getActivity();
        orderList=new ArrayList<>();
        CollectionReference dbOrders=db.collection("Orders");
        dbOrders.addSnapshotListener(new EventListener<QuerySnapshot>() {


            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                orderList.clear();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    final Order order;
                    int serveTime=0;
                    if(documentSnapshot.getLong("Time")!=null)
                    {serveTime=(Math.toIntExact((Long)documentSnapshot.get("Time")));}
                    String orderStatus=((String)documentSnapshot.get("Status"));
                    int orderID=0;
                    if(documentSnapshot.getLong("Table")!=null)
                    {orderID=(Math.toIntExact((Long) documentSnapshot.get("Table")));}
                    ArrayList<Object>dishItems= (ArrayList<Object>) documentSnapshot.getData().get("Items"); //array of dishes and status for one order
                    order=new Order(documentSnapshot.getId(),serveTime,null,orderStatus,orderID);
                    if(dishItems!=null && dishItems.size()>0)
                    {
                        for ( final Object dishitem: dishItems)
                        {
                            Map<String, Object> myMap = (Map<String, Object>) dishitem;
                            final String itemStatus=((String) myMap.get("itemStatus"));
                            final DocumentReference dbDish = FirebaseFirestore.getInstance().document( myMap.get("foodItem").toString()); //get document reference
                            Log.i("document id",dbDish.getId());
                            Log.i("path id",dbDish.getPath());
                            dbDish.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String  dishName=documentSnapshot.getString("Name");
                                    String   dishType=documentSnapshot.getString("type");
                                    int dishTime=0;
                                    if(documentSnapshot.getLong("Time")!=null)
                                    {  dishTime=Math.toIntExact(documentSnapshot.getLong("Time"));}
                                    order.addDish(new Dish(null,dbDish.getPath(),dishName,dishTime,0,itemStatus,dishType));
                                    //Log.i("Dishname",dishName);
                                }
                            });

                        }
                    }
                    if(order.getStatus()!=null && order.getStatus().compareTo("ready")!=0)
                    {
                        orderList.add(order);
                        orderAdapter.notifyDataSetChanged();}
                }
            }
        });
        setorders();
        return root;

    }

    public void setorders()
    {
        orderAdapter = new OrderAdapter(orderList,R.layout.kitchen_order_holder);
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



