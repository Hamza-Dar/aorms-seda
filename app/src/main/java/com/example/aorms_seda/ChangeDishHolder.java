package com.example.aorms_seda;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChangeDishHolder extends RecyclerView.ViewHolder {


    //initialize variables

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

    public void setValues(final Context context, final RequestsChange Request)
    {
        dishName.setText(Request.dishInfo);
        dishStatus.setText(Request.status);
        requestType.setText(Request.getRequest());


        //listener for accept button;


        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //firebase update dish request status


                if(Request.request.compareTo("Add")==0)
                {
                    //Add request then arrayUnion Add;
                    final DocumentReference orderRef=db.collection("Orders").document(Request.orderId);
                    DocumentReference foodRef=db.collection("Fooditem").document(Request.dishID);
                    final Map<String,Object> mymap=new HashMap<>();
                    mymap.put("foodItem",foodRef);
                    mymap.put("itemStatus","waiting");


                    orderRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            ArrayList<Object> Items= (ArrayList<Object>) documentSnapshot.get("Items");
                            if (Items!=null) {
                                Items.add(mymap);
                                orderRef.update("Items", Items);
                            }
                            else
                            {
                                Toast.makeText(context,"Items not found",Toast.LENGTH_SHORT);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"Error loading data",Toast.LENGTH_SHORT);
                        }
                    });

                    //remove request from  requests

                    final Map<String,Object> mymap2=new HashMap<>();
                    mymap2.put("foodItem",foodRef);
                    mymap2.put("type",Request.request);
                    mymap2.put("decision","pending");
                    mymap2.put("itemStatus",Request.status);
                    String requestID=Request.requestID;
                    //firebase update dish request status
                    db.collection("OrderRequest").document(Request.requestID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            ArrayList<Object> Items= (ArrayList<Object>) documentSnapshot.get("Requests");
                            if(Items!=null && Items.size()>0) {
                                boolean check = false;
                                for (int i = 0; i < Items.size() && check != true; i++) {
                                    Map<String, Object> map = (Map<String, Object>) Items.get(i);
                                    String status = (String) map.get("itemStatus");
                                    String decision = (String) map.get("decision");
                                    String type = (String) map.get("type");
                                    DocumentReference foodItem = (DocumentReference) map.get("foodItem");
                                    String id = foodItem.getId();
                                    //////////
                                    String status2 = (String) mymap2.get("itemStatus");
                                    String decision2 = (String) mymap2.get("decision");
                                    String type2 = (String) mymap2.get("type");
                                    DocumentReference foodItem2 = (DocumentReference) mymap2.get("foodItem");
                                    String id2 = foodItem2.getId();
                                    if (status.compareTo(status2) == 0 && type.compareTo(type2) == 0 && id.compareTo(id2) == 0) {

                                        Items.remove(i);
                                        check = true;
                                    }
                                }
                                db.collection("OrderRequest").document(Request.requestID).update("Requests", Items);
                            }
                            else
                            {
                                Toast.makeText(context,"Requests not found",Toast.LENGTH_SHORT);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"Error loading data",Toast.LENGTH_SHORT);
                        }
                    });
                }
                else if(Request.request.compareTo("Delete")==0)
                {
                    DocumentReference orderRef=db.collection("Orders").document(Request.orderId);
                    DocumentReference foodRef=db.collection("Fooditem").document(Request.dishID);
                    final Map<String,Object> mymap=new HashMap<>();
                    mymap.put("foodItem",foodRef);
                    mymap.put("itemStatus",Request.status);
                    orderRef.update("Items",FieldValue.arrayRemove(mymap)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context,"Updated Data successfully",Toast.LENGTH_SHORT);
                        }
                    }); //remove from orders;
                    orderRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            ArrayList<Object> Items= (ArrayList<Object>) documentSnapshot.get("Items");
                            if(Items!=null && Items.size()>0) {
                                boolean check = false;
                                for (int i = 0; i < Items.size() && check != true; i++) {
                                    Map<String, Object> map = (Map<String, Object>) Items.get(i);
                                    String status = (String) map.get("itemStatus");
                                    DocumentReference foodItem = (DocumentReference) map.get("foodItem");
                                    String id = foodItem.getId();

                                    String status2 = (String) mymap.get("itemStatus");
                                    DocumentReference foodItem2 = (DocumentReference) mymap.get("foodItem");
                                    String id2 = foodItem.getId();

                                    if (status.compareTo(status2) == 0 && id.compareTo(id2) == 0) {
                                        Items.remove(i);
                                        check = true;
                                    }
                                }
                                db.collection("Orders").document(Request.orderId).update("Items", Items);
                            }
                            else
                            {
                                Toast.makeText(context,"No dishes found",Toast.LENGTH_SHORT);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"Error loading data",Toast.LENGTH_SHORT);
                        }
                    });


                    //remove request from reference;
                    final Map<String,Object> mymap2=new HashMap<>();
                    mymap2.put("foodItem",foodRef);
                    mymap2.put("type","Delete");
                    mymap2.put("decision","pending");
                    mymap2.put("itemStatus",Request.status);
                    String requestID=Request.requestID;
                    //firebase update dish request status
                    db.collection("OrderRequest").document(Request.requestID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            ArrayList<Object> Items= (ArrayList<Object>) documentSnapshot.get("Requests");
                            if(Items!=null && Items.size()>0) {
                                boolean check = false;
                                for (int i = 0; i < Items.size() && check != true; i++) {
                                    Map<String, Object> map = (Map<String, Object>) Items.get(i);
                                    String status = (String) map.get("itemStatus");
                                    String decision = (String) map.get("decision");
                                    String type = (String) map.get("type");
                                    DocumentReference foodItem = (DocumentReference) map.get("foodItem");
                                    String id = foodItem.getId();
                                    //////////
                                    String status2 = (String) mymap2.get("itemStatus");
                                    String decision2 = (String) mymap2.get("decision");
                                    String type2 = (String) mymap2.get("type");
                                    DocumentReference foodItem2 = (DocumentReference) mymap2.get("foodItem");
                                    String id2 = foodItem2.getId();
                                    if (status.compareTo(status2) == 0  && type.compareTo(type2) == 0 && id.compareTo(id2) == 0) {

                                        Items.remove(i);
                                        check = true;
                                    }
                                }
                                db.collection("OrderRequest").document(Request.requestID).update("Requests", Items);
                            }
                            else
                            {
                                Toast.makeText(context,"No Requests found",Toast.LENGTH_SHORT);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"Error loading data",Toast.LENGTH_SHORT);
                        }
                    });
                }

                rejectBtn.setText("Accepted");
                rejectBtn.setEnabled(false);
                acceptBtn.setVisibility(View.GONE);
            }
        });




        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        //listener for reject button

        rejectBtn.setOnClickListener(new View.OnClickListener() {  //reject request and remove from list
            @Override
            public void onClick(View view) {

                DocumentReference orderRef=db.collection("Orders").document(Request.orderId);
                DocumentReference foodRef=db.collection("Fooditem").document(Request.dishID);


                //remove request from reference;
                final Map<String,Object> mymap2=new HashMap<>();
                mymap2.put("foodItem",foodRef);
                mymap2.put("type",Request.request);
                mymap2.put("decision","pending");
                mymap2.put("itemStatus",Request.status);
                String requestID=Request.requestID;
                //firebase update dish request status
                db.collection("OrderRequest").document(Request.requestID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        ArrayList<Object> Items= (ArrayList<Object>) documentSnapshot.get("Requests");
                        if(Items!=null && Items.size()>0)
                        {boolean check=false;
                            for(int i=0;i<Items.size()&& check!=true;i++)
                            {
                                Map<String,Object>map= (Map<String, Object>) Items.get(i);
                                String status= (String) map.get("itemStatus");
                                String decision= (String) map.get("decision");
                                String type= (String) map.get("type");
                                DocumentReference foodItem= (DocumentReference) map.get("foodItem");
                                String id=foodItem.getId();
                                //////////
                                String status2= (String) mymap2.get("itemStatus");
                                String decision2= (String) mymap2.get("decision");
                                String type2= (String) mymap2.get("type");
                                DocumentReference foodItem2= (DocumentReference) mymap2.get("foodItem");
                                String id2=foodItem2.getId();
                                if(status!=null && status2!=null && status.compareTo(status2) ==0)
                                {
                                    Items.remove(i);
                                    check=true;
                                }
                            }
                            db.collection("OrderRequest").document(Request.requestID).update("Requests",Items);
                        }
                        else
                        {
                            Toast.makeText(context,"No requests found",Toast.LENGTH_SHORT);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error loading data",Toast.LENGTH_SHORT);
                    }
                });

                rejectBtn.setText("Rejected");
                rejectBtn.setEnabled(false);
                acceptBtn.setVisibility(View.GONE);
            }
        });


    }
}