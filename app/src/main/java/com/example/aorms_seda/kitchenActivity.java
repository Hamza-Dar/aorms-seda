package com.example.aorms_seda;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class kitchenActivity extends AppCompatActivity {

    //load cooks;

    private FirebaseFirestore db;
    ArrayList<Cook>chefList;
    String chefName;
    int chefId;
    int currentFragment;

    /////////for orders

    ArrayList<Order>orderList;
    int sizeOfCooks;
    ArrayList<Map<String,Object>>orderIDs;

    ArrayList<Dish> dishes;
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen_activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("DishInfo"));
        orderIDs=new ArrayList<>();
        currentFragment=0;
        sizeOfCooks=5;
        dishes = new ArrayList<>();
        bundle=  new Bundle();
        intent=new Intent();
        BottomNavigationView bottomNav=findViewById(R.id.kitchenBottomnav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        db=FirebaseFirestore.getInstance();
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(false)
//                .build();
//        db.setFirestoreSettings(settings);
        CollectionReference dbChefs=db.collection("Employee");
        chefList=new ArrayList<>();
        orderList=new ArrayList<>();

        //get chefs from firebase;
        dbChefs.whereEqualTo("Job","Cook").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {

                    chefName=(String) documentSnapshot.getData().get("Name");
                    chefId=0;
                    if(documentSnapshot.getLong("ID")!=null)
                    {chefId=Math.toIntExact(documentSnapshot.getLong("ID"));}
                    ArrayList<String>speciality= (ArrayList<String>) documentSnapshot.getData().get("speciality");
                    chefList.add(new Cook(chefId,chefName,0,speciality));

                }

                bundle.putSerializable("chefsList",chefList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error loading data",Toast.LENGTH_SHORT);
            }
        });


        //default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.kitchenFrame,new KitchenHomeFragment()).commit();


        //add firebase real time listener for orders;
        db.collection("Orders")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                DocumentSnapshot documentSnapshot=dc.getDocument();
                                //new order add it to order list
                                Log.i("document id323",documentSnapshot.getId());
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

                                //
                                String priority;
                                if(documentSnapshot.getString("Priority")!=null)
                                {
                                    priority=documentSnapshot.getString("Priority");
                                    order.setPriority(priority);
                                }

                                if(dishItems!=null && dishItems.size()>0)
                                {
                                    for ( final Object dishitem: dishItems)
                                    {
                                        Map<String, Object> myMap = (Map<String, Object>) dishitem;
                                        final String itemStatus=String.valueOf(myMap.get("itemStatus"));
                                        Log.i("document ref213",myMap.get("foodItem").toString()+ "  "+ documentSnapshot.getId());

                                        final DocumentReference dbDish =  (DocumentReference)FirebaseFirestore.getInstance().document( myMap.get("foodItem").toString()); //get document reference
                                        Log.i("document id",dbDish.getId());
                                        Log.i("path id",dbDish.getPath());
                                        dbDish.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                String  dishName=documentSnapshot.getString("Name");
                                                String   dishType=documentSnapshot.getString("type");
                                                int dishTime=0;
                                                if(documentSnapshot.getLong("Time")!=null)
                                                { dishTime=Math.toIntExact(documentSnapshot.getLong("Time"));}
                                                order.addDish(new Dish(null,dbDish.getPath(),dishName,dishTime,0,itemStatus,dishType));
                                                //Log.i("Dishname",dishName);
                                                EnqueueDish(order,order.dishes.size()-1);
                                            }
                                        });

                                    }
                                }
                                orderList.add(order);
                            }
                            else if(dc.getType()==DocumentChange.Type.MODIFIED) {
                                DocumentSnapshot documentSnapshot = dc.getDocument();
                                //modified order;
                                //check id in order list and replace and remove the done dishes from list;
                                final Order order;
                                int serveTime=0;
                                if(documentSnapshot.getLong("Time")!=null)
                                { serveTime = (Math.toIntExact((Long) documentSnapshot.get("Time")));}
                                String orderStatus = ((String) documentSnapshot.get("Status"));
                                if (orderStatus.compareTo("ready") != 0) {
                                    int orderID=0;
                                    if(documentSnapshot.getLong("Table")!=null)
                                    {orderID = (Math.toIntExact((Long) documentSnapshot.get("Table")));}
                                    ArrayList<Object> dishItems = (ArrayList<Object>) documentSnapshot.getData().get("Items"); //array of dishes and status for one order
                                    order = new Order(documentSnapshot.getId(), serveTime, null, orderStatus, orderID);
                                    if(dishItems!=null && dishItems.size()>0) {
                                        for (final Object dishitem : dishItems) {
                                            Map<String, Object> myMap = (Map<String, Object>) dishitem;
                                            final String itemStatus = ((String) myMap.get("itemStatus"));
                                            if (itemStatus.compareTo("done") != 0) {
                                                final DocumentReference dbDish = (DocumentReference) FirebaseFirestore.getInstance().document( myMap.get("foodItem").toString()); //get document reference
                                                Log.i("document id", dbDish.getId());
                                                Log.i("path id", dbDish.getPath());
                                                dbDish.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        String dishName = documentSnapshot.getString("Name");
                                                        String dishType = documentSnapshot.getString("type");
                                                        int dishTime=0;
                                                        if(documentSnapshot.getLong("Time")!=null)
                                                        {dishTime = Math.toIntExact(documentSnapshot.getLong("Time"));}
                                                        order.addDish(new Dish(null, dbDish.getPath(), dishName, dishTime, 0, itemStatus, dishType));
                                                        //    Log.i("Dishname", dishName);
                                                    }
                                                });
                                            }
                                        }
                                    }
                                    //replace in the orderlist and replace in orderIDs too;
                                    boolean check=false;
                                    for(int i=0;i<orderList.size() && check!=true;i++)
                                    {
                                        if(orderList.get(i).orderId.compareTo(order.getOrderId())==0)
                                        {
                                            orderList.set(i,order);
                                            check=true;
                                        }
                                    }
                                    check=false;
                                    for(int i=0;i<orderIDs.size()&&check!=true;i++)
                                    {
                                        Map<String,Object>map=orderIDs.get(i);
                                        Order newOrder=(Order)map.get("order");
                                        if(newOrder.orderId.compareTo(order.getOrderId())==0)
                                        {
                                            check=true;
                                            map.put("order",order);
                                            orderIDs.set(i,map);
                                        }
                                    }
                                }
                            }
                        }

                    }
                });

    }

    //bottom navigation menu

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment SelectedFragment=null;
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    currentFragment=0;
                    SelectedFragment=new KitchenHomeFragment();
                    break;
                case R.id.nav_queues:

                    currentFragment=1;
                    bundle.putSerializable("dishes",dishes);
                    bundle.putSerializable("chefList",chefList);
                    SelectedFragment=new QueuesFragment();
                    SelectedFragment.setArguments(bundle);
                    break;
                case R.id.nav_notify:
                    currentFragment=2;
                    SelectedFragment=new OrdersFragment();
                    break;
                case R.id.nav_request:
                    currentFragment=3;
                    SelectedFragment=new OrderRequestFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.kitchenFrame,SelectedFragment).commit();
            return true;
        }
    };


    //enqueue dishes
    public void EnqueueDish(Order order, int k)
    {

        int workLoad = 2;
        int size = chefList.size();
        boolean cookAssigned = false;

        if (order.getDishes() != null && order.getDishes().size()>k && order.getDishes().get(k)!=null && order.getDishes().get(k).getStatus().compareTo("done")!=0) {
            //for each dish

            Dish dish1 = order.getDishes().get(k);
            if (size > 0) // checks presence of cook in arraylist
            {
                for (int i = 0; i < size && cookAssigned!=true; i++) {   //for no of cooks; check on the basis of speciality
                    boolean check = true;
                    for (int j = 0; (j < chefList.get(i).getSpeciality().size()) && check; j++) {
                        if (dish1.category!=null && chefList.get(i).getSpeciality().get(j).compareTo( dish1.getCategory())==0) { //if speaciality and workload
                            check = false;
                            if (chefList.get(i).getWorkload() < workLoad) {
                                dish1.setCookId(chefList.get(i).getCookId());
                                chefList.get(i).incrementWorkLoad();
                                cookAssigned = true;
                            }
                        }
                    }
                }
                if (cookAssigned == false) {  // on the basis of workload
                    for (int i = 0; i < size && cookAssigned!=true; i++) {
                        if (chefList.get(i).getWorkload() < workLoad) {
                            dish1.setCookId(chefList.get(i).getCookId());
                            chefList.get(i).incrementWorkLoad();
                            cookAssigned = true;
                        }
                    }
                }
                if (cookAssigned==false) {  //add to waiting list

                    Map<String,Object> map=new HashMap<>();
                    map.put("order",order);
                    map.put("index",k);
                    orderIDs.add(map);
                }
                else
                {
                    dishes.add(dish1);
                    order.getDishes().set(k, dish1);
                }
            }


        }
    }



    //receive broadcast message

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            boolean check=false;
            int chefID=0;
            Dish dishinfo = (Dish) intent.getSerializableExtra("DishInfoStatus");
            chefID=dishinfo.cookId;
            boolean checkWorkload=false;
            for(int i=0;i<dishes.size() && check!=true;i++)
            {
                if(dishinfo.orderId==dishes.get(i).orderId && dishinfo.getDishId()==dishes.get(i).getDishId() && dishinfo.getStatus().compareTo("done")==0)
                {
                    dishes.remove(i);

                    //decrement workload of chefs
                    for(int k=0;k<chefList.size() && checkWorkload!=true;k++)
                    {
                        if(chefID==chefList.get(k).cookId)
                        {
                            chefList.get(k).decrementWorkLoad();
                            checkWorkload=true;
                        }
                    }


                    //add a dish from waiting to queue
                    if(orderIDs!=null && orderIDs.size()>0)
                    {
                        boolean found=false;
                        int foundIndex=0;
                        for(int m=0;m<orderIDs.size() && found!=true;m++)
                        {
                            Map<String,Object> tempMap=orderIDs.get(m);
                            Order tempOrder= (Order) tempMap.get("order");
                            if(tempOrder.priority!=null && tempOrder.priority.compareTo("high")==0)
                            {
                                foundIndex=m;
                                found=true;
                            }
                        }
                        if(found==false)
                        {
                            foundIndex=0;
                        }

                        Map<String,Object> map=orderIDs.get(foundIndex);
                        Order order= (Order) map.get("order");
                        int index= (int) map.get("index");
                        orderIDs.remove(foundIndex);
                        EnqueueDish(order,index);
                    }
                    check=true;
                }
                else if(dishinfo.orderId==dishes.get(i).orderId && dishinfo.getDishId()==dishes.get(i).getDishId() && dishinfo.getStatus().compareTo("progress")==0)
                {
                    dishes.get(i).setStatus("progress");
                    check=true;
                }
            }
            if(currentFragment==1)
            {
                bundle.putSerializable("dishes",dishes);
                bundle.putSerializable("chefList",chefList);
                Fragment SelectedFragment=new QueuesFragment();
                SelectedFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.kitchenFrame,SelectedFragment).commit();
            }
            //remove from dish or change status accordingly and send update to the queue fragment
        }
    };
}
