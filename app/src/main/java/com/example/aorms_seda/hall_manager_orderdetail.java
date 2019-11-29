package com.example.aorms_seda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class hall_manager_orderdetail extends AppCompatActivity {
    TextView orderno;
    TextView status;
    TextView tableno;
    TextView time;
    TextView dishes;
    TextView total;
    TextView waiterslist;
    Button billgenerate;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String orderdishes;
    double bill=0;
    int x;
    String stauscheck=null;
    String ordernumber=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_manager_orderdetail);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> message=new ArrayList<>();
        message = bundle.getStringArrayList("message");
        orderno=findViewById(R.id.orderno);
        status=findViewById(R.id.status);
        tableno=findViewById(R.id.tableno);
        time=findViewById(R.id.time);
        dishes=findViewById(R.id.dishes);
        waiterslist=findViewById(R.id.waiterslist);
        total=findViewById(R.id.bill);
        orderno.setText(message.get(0));
        status.setText(message.get(1));
        tableno.setText(message.get(2));
        time.setText(message.get(3));
        stauscheck=message.get(1);
        ordernumber=message.get(0);



        DocumentReference noteRef = db.document("Orders/"+message.get(0));
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
if (documentSnapshot.exists()) {
////////////start
final List<DocumentReference>references= new ArrayList<>();
hall_order city = documentSnapshot.toObject(hall_order.class);
Log.d("statuss", city.items.toString());
for (int i=0;i<city.items.size();i++){
    Map<String, Object> map1=city.items.get(i);
    for (Map.Entry<String, Object> entry : map1.entrySet()) {
        if (entry.getKey().equals("foodItem")) {
            DocumentReference documentReference = (DocumentReference)entry.getValue();
            references.add(documentReference);
            Log.d("hfdd","Next token is : "+i + documentReference);
        }
    }

}

for (x =0 ; x <references.size();x++) {
references.get(x).get()
.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.exists()) {
            String title = documentSnapshot.getString("Name");
            String dis=dishes.getText().toString();
            if(dis.equals(""))
                orderdishes=title;
            else
                orderdishes = dis + ", " + title;
            dishes.setText(orderdishes);
            double price = documentSnapshot.getDouble("Price");
            String b = total.getText().toString();
            double temp =Double.valueOf(b);
            bill = temp + price;
            total.setText(String.valueOf(bill));
        } else {
        }
    }
})
.addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
    }
});
}

} else {
    //Toast.makeText(this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

db.collection("Waiterfororder")
        .whereArrayContains("Orders", ordernumber)
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String waiterss="";
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("abc",document.getData().toString());
                        if(waiterss.equalsIgnoreCase(""))
                             waiterss=document.get("name").toString();
                        else
                            waiterss=waiterss+", "+document.get("name").toString();

                    }
                    waiterslist.setText(waiterss);
                } else {
                    Log.d("abc", "Error getting documents: ", task.getException());
                }
            }
        });

    }
    public void clicker(View v){
        int a=0;

        if ((stauscheck.equalsIgnoreCase("complete"))|| (stauscheck.equalsIgnoreCase("prepared")) || (stauscheck.equalsIgnoreCase("ready"))){

            db.collection("Waiterfororder")
                    .whereArrayContains("Orders", ordernumber)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String counter =document.get("Count").toString();
                                    int x=Integer.parseInt(counter);
                                    x--;
                                    String count =String.valueOf(x);
                                    Map<String,Object> updates = new HashMap<>();
                                    updates.put("Count", count);
                                    updates.put("status", "Available");
                                    Log.d("abc", document.get("Count").toString() + " => " + document.getData());
                                    DocumentReference washingtonRef = db.collection("Waiterfororder").document(document.getId());
                                    washingtonRef.update("Orders", FieldValue.arrayRemove(ordernumber));
                                    DocumentReference docRef = db.collection("Waiterfororder").document(document.getId());
                                    docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("Moiz AHmed", "DocumentSnapshot successfully updated!");
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("", "Error updating document", e);
                                                }
                                            });


                                }
                                Map<String,Object> updates = new HashMap<>();
                                updates.put("Status", "Paid");
                                //Log.d("abc", document.get("Count").toString() + " => " + document.getData());
                                DocumentReference washingtonRef = db.collection("Orders").document(ordernumber);
                                DocumentReference docRef = db.collection("Orders").document(ordernumber);
                                docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pay();
                                        Log.d("Moiz AHmed", "DocumentSnapshot successfully updated!");
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("", "Error updating document", e);
                                            }
                                        });

                            } else {
                                Log.d("abc", "Error getting documents: ", task.getException());
                            }
                        }
                    });

        } else{
            Toast.makeText(this, "Unable to Mark Bill Paid of Incomplete Order", Toast.LENGTH_SHORT).show();
        }
    }
    void pay(){
        Toast.makeText(this,"Mark as Paid Successfully",Toast.LENGTH_SHORT).show();
    }
}
