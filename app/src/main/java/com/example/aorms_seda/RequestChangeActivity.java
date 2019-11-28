package com.example.aorms_seda;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RequestChangeActivity extends AppCompatActivity {

    RecyclerView dishes;
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
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen_activity_order_details);
        dishes = (RecyclerView) findViewById(R.id.dishesrcv);

        Intent i=getIntent();
        dishList=new ArrayList<>();
        dishList= (ArrayList<RequestsChange>) i.getSerializableExtra("requests");
        orderInfo= (Order) i.getSerializableExtra("order");

        ordertxt=findViewById(R.id.orderIdtxtview);
        statustxt=findViewById(R.id.serveTimetxtview);
        orderStatus=findViewById(R.id.orderStatustxtview);
        ordertxt.setText(String.valueOf(orderInfo.getOrderId()));
        statustxt.setText(String.valueOf(orderInfo.getServeTime()));
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


        db=FirebaseFirestore.getInstance();

        if(dishList==null || dishList.isEmpty())
        {

            dishesOrder.setVisibility(View.GONE);
            dishes.setVisibility(View.GONE);
            cancelbtn.setText("Cancel Order");
            doneOrderbtn.setVisibility(View.VISIBLE);

            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //cancel order...
                    final DocumentReference orderRef= db.collection("Orders").document(orderInfo.getOrderId());
                    orderRef.delete();

                    //remove request
                    db.collection("OrderRequest").whereEqualTo("orderID",orderRef).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                            {

                                   DocumentReference requestRef=documentSnapshot.getReference();
                                    requestRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            cancelbtn.setText("Cancelled");
                                            doneOrderbtn.setVisibility(View.GONE);
                                        }
                                    });


                            }
                        }
                    });
                }
            });

            doneOrderbtn.setText("Keep Order");
            doneOrderbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final DocumentReference orderRef= db.collection("Orders").document(orderInfo.getOrderId());
                    //remove request
                    db.collection("OrderRequest").whereEqualTo("orderID",orderRef).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                            {

                                   DocumentReference requestRef=documentSnapshot.getReference();
                                    requestRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            cancelbtn.setText("Cancel Rejected");
                                            doneOrderbtn.setVisibility(View.GONE);
                                        }
                                    });

                                }
                            }

                    });
                }
            });
        }
        else
        {
            cancelbtn.setVisibility(View.GONE);
            doneOrderbtn.setVisibility(View.VISIBLE);
            doneOrderbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //finish();
                }
            });
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
        dishAdapter=new ChangeDishAdapter(dishList,R.layout.kitchen_change_dish_holder);
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
