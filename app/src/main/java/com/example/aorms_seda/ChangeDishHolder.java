package com.example.aorms_seda;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

public class ChangeDishHolder extends RecyclerView.ViewHolder {

    TextView requestType;
    TextView dishName;
    TextView dishStatus;
    Button acceptBtn;
    Button rejectBtn;
    private FirebaseFirestore db;



    public ChangeDishHolder(@NonNull View itemView) {
        super(itemView);
        requestType=itemView.findViewById(R.id.requestTypetxtview);
        dishName=itemView.findViewById(R.id.dishNametxtview);
        dishStatus=itemView.findViewById(R.id.dishStatustxtview);
        acceptBtn=itemView.findViewById(R.id.acceptChangeBtn);
        rejectBtn=itemView.findViewById(R.id.rejectChangeBtn);
        db=FirebaseFirestore.getInstance();

    }

    public void setValues(RequestsChange Request)
    {
        dishName.setText(Request.dishInfo);
        dishStatus.setText(Request.status);
        requestType.setText(Request.getRequest());
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //firebase update dish request status

                rejectBtn.setText("Accepted");
                rejectBtn.setEnabled(false);
                acceptBtn.setVisibility(View.GONE);
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //firebase update dish request status

                rejectBtn.setText("Rejected");
                rejectBtn.setEnabled(false);
                acceptBtn.setVisibility(View.GONE);
            }
        });


    }
}