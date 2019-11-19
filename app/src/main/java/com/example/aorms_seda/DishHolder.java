package com.example.aorms_seda;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DishHolder extends RecyclerView.ViewHolder {

    TextView dishName;
    TextView dishStatus;
    Button donebtn;
    Button startbtn;

    private FirebaseFirestore db;

    public DishHolder(@NonNull View itemView) {
        super(itemView);
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

        if(pos==1)
        {
            //hide buttons
            donebtn.setVisibility(View.GONE);
            startbtn.setVisibility(View.GONE);
        }

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //update dish status in firebase
                Map<String,Object> mymap=new HashMap<>();
                String[] vals=DishInfo.getDishId().split("/");
                String val1=vals[0];
                String val2=vals[1];
                DocumentReference dbRef=db.collection(val1).document(val2);
                mymap.put("foodItem",dbRef);
                mymap.put("itemStatus",DishInfo.getStatus());
                db.collection("Orders").document(DishInfo.getOrderId()).update("Items", FieldValue.arrayRemove(mymap));

                Map<String,Object> mymap2=new HashMap<>();
                mymap2.put("foodItem",dbRef);
                mymap2.put("itemStatus","done");
                db.collection("Orders").document(DishInfo.getOrderId()).update("Items", FieldValue.arrayUnion(mymap2));


                DishInfo.setStatus("done");
                Intent intent = new Intent("DishInfo");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("DishInfoStatus",DishInfo);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


            }
        });


        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Map<String,Object> mymap=new HashMap<>();
                String[] vals=DishInfo.getDishId().split("/");
                String val1=vals[0];
                String val2=vals[1];
                DocumentReference dbRef=db.collection(val1).document(val2);
                mymap.put("foodItem",dbRef);
                mymap.put("itemStatus",DishInfo.getStatus());
                db.collection("Orders").document(DishInfo.getOrderId()).update("Items", FieldValue.arrayRemove(mymap));

                Map<String,Object> mymap2=new HashMap<>();
                mymap2.put("foodItem",dbRef);
                mymap2.put("itemStatus","InProgress");
                db.collection("Orders").document(DishInfo.getOrderId()).update("Items", FieldValue.arrayUnion(mymap2));


                DishInfo.setStatus("InProgress");
                Intent intent = new Intent("DishInfo");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("DishInfoStatus",DishInfo);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });


    }
}
