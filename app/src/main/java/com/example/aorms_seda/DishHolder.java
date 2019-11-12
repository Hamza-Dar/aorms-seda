package com.example.aorms_seda;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

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

    public void setValues(Dish DishInfo,int pos)
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
            }
        });


        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //update dish status in firebase
            }
        });


    }
}
