package com.example.aorms_seda;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DishHolder extends RecyclerView.ViewHolder {

    TextView dishName;
    TextView dishStatus;
    TextView orderID;
    Button donebtn;
    Button startbtn;

    private FirebaseFirestore db;

    public DishHolder(@NonNull View itemView) {
        super(itemView);

        orderID=itemView.findViewById(R.id.dishOrdertxtview);
        dishName=itemView.findViewById(R.id.dishNametxtview);
        dishStatus=itemView.findViewById(R.id.dishStatustxtview);
        donebtn=itemView.findViewById(R.id.donebtn);
        startbtn=itemView.findViewById(R.id.progressbtn);
        db=FirebaseFirestore.getInstance();
    }

    public void setValues(final Context context, final Dish DishInfo, int pos)
    {
        dishName.setText(DishInfo.getName());
        dishStatus.setText(DishInfo.getStatus());
        orderID.setText(DishInfo.orderId);

        if(pos==1)
        {
            //hide buttons
            donebtn.setVisibility(View.GONE);
            startbtn.setVisibility(View.GONE);
            orderID.setVisibility(View.GONE);
        }


        //listener for done button
        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //update dish status in firebase
                final Map<String,Object> mymap=new HashMap<>();
                String[] vals=DishInfo.getDishId().split("/");
                String val1=vals[0];
                String val2=vals[1];
                DocumentReference dbRef=db.collection(val1).document(val2);
                mymap.put("foodItem",dbRef.getPath());
                mymap.put("itemStatus",DishInfo.getStatus());
                final Map<String,Object> mymap2=new HashMap<>();
                mymap2.put("foodItem",dbRef.getPath());
                mymap2.put("itemStatus","done");


                db.collection("Orders").document(DishInfo.getOrderId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        ArrayList<Object>Items= (ArrayList<Object>) documentSnapshot.get("Items");
                        if(Items!=null && Items.size()>0) {
                            boolean check = false;
                            for (int i = 0; i < Items.size() && check != true; i++) {
                                Map<String, Object> map = (Map<String, Object>) Items.get(i);
                                DocumentReference foodRef = (DocumentReference) FirebaseFirestore.getInstance().document( map.get("foodItem").toString()); //get document reference
                                String itemStatus = (String) map.get("itemStatus");
                                String id = foodRef.getId();


                                DocumentReference foodRef2 = (DocumentReference) FirebaseFirestore.getInstance().document( mymap.get("foodItem").toString()); //get document reference
                                String itemStatus2 = (String) mymap.get("itemStatus");
                                String id2 = foodRef2.getId();
                                if (itemStatus!=null && id.compareTo(id2) == 0 && itemStatus.compareTo(itemStatus2) == 0) {
                                    Items.set(i, mymap2);
                                    check = true;
                                }
                            }
                            db.collection("Orders").document(DishInfo.getOrderId()).update("Items", Items);
                        }
                        else {
                            Toast.makeText(context,"No dishes found",Toast.LENGTH_SHORT);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error loading data",Toast.LENGTH_SHORT);
                    }
                });



                DishInfo.setStatus("done");
                Intent intent = new Intent("DishInfo");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("DishInfoStatus",DishInfo);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


            }
        });


        //listener for start button
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Map<String,Object> mymap=new HashMap<>();
                String[] vals=DishInfo.getDishId().split("/");
                String val1=vals[0];
                String val2=vals[1];
                DocumentReference dbRef=db.collection(val1).document(val2);
                mymap.put("foodItem",dbRef.getPath());
                mymap.put("itemStatus",DishInfo.getStatus());

                final Map<String,Object> mymap2=new HashMap<>();
                mymap2.put("foodItem",dbRef.getPath());
                mymap2.put("itemStatus","progress");


                db.collection("Orders").document(DishInfo.getOrderId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        ArrayList<Object>Items= (ArrayList<Object>) documentSnapshot.get("Items");
                        if(Items!=null && Items.size()>0)
                        {boolean check=false;
                            for (int i=0;i<Items.size()&& check!=true;i++)
                            {
                                Map<String,Object>map= (Map<String, Object>) Items.get(i);
                                DocumentReference foodRef= (DocumentReference)FirebaseFirestore.getInstance().document( map.get("foodItem").toString()); //get document reference
                                String itemStatus= (String) map.get("itemStatus");
                                String id=foodRef.getId();


                                DocumentReference foodRef2= (DocumentReference)FirebaseFirestore.getInstance().document( mymap.get("foodItem").toString()); //get document reference
                                String itemStatus2= (String) mymap.get("itemStatus");
                                String id2=foodRef2.getId();
                                if(itemStatus!=null &&itemStatus2!=null &&id.compareTo(id2)==0&&itemStatus.compareTo(itemStatus2)==0)
                                {
                                    Items.set(i,mymap2);
                                    check=true;
                                }
                            }
                            db.collection("Orders").document(DishInfo.getOrderId()).update("Items",Items);}
                        else
                        {
                            Toast.makeText(context,"No Dieshes found",Toast.LENGTH_SHORT);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error loading data",Toast.LENGTH_SHORT);
                    }
                });


                DishInfo.setStatus("progress");
                Intent intent = new Intent("DishInfo");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("DishInfoStatus",DishInfo);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });


    }
}
