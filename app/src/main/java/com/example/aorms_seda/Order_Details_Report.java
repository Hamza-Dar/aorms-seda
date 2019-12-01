package com.example.aorms_seda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Order_Details_Report extends AppCompatActivity {

    TextView orderno;
    TextView status;
    TextView tableno;
    TextView time;
    TextView dishes;
    TextView total;
    TextView waiterslist;
    TextView prioiry;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String orderdishes;
    double bill=0;
    int x;
    String stauscheck=null;
    String ordernumber=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__details__report);
        orderno=findViewById(R.id.orderno1);
        tableno=findViewById(R.id.tableno1);
        dishes=findViewById(R.id.dishes1);
        waiterslist=findViewById(R.id.waiterslist1);
        total=findViewById(R.id.bill1);
        prioiry= findViewById(R.id.priority1);
        setView();
        orderno.setText(getIntent().getStringExtra("ID"));
    }

    void setView(){
        String id = getIntent().getStringExtra("ID");
        DocumentReference noteRef = db.document("Orders/" + id);
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
////////////start
                            final List<String> references = new ArrayList<>();
                            tableno.setText(documentSnapshot.get("Table").toString());
                            hall_order city = documentSnapshot.toObject(hall_order.class);
                            Log.d("statuss", city.items.toString());
                            for (int i = 0; i < city.items.size(); i++) {
                                Map<String, Object> map1 = city.items.get(i);
                                for (Map.Entry<String, Object> entry : map1.entrySet()) {
                                    if (entry.getKey().equals("foodItem")) {
                                        String documentReference = (String) entry.getValue();
                                        references.add(documentReference);
                                        Log.d("hfdd", "Next token is : " + i + documentReference);
                                    }
                                }

                            }

                            for (x = 0; x < references.size(); x++) {
                                DocumentReference noteRef = db.document(references.get(x));
                                noteRef.get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    String title = documentSnapshot.getString("Name");
                                                    String dis = dishes.getText().toString();
                                                    if (dis.equals(""))
                                                        orderdishes = title;
                                                    else
                                                        orderdishes = dis + ", " + title;
                                                    dishes.setText(orderdishes);
                                                    double price = documentSnapshot.getDouble("Price");
                                                    String b = total.getText().toString();
                                                    double temp = Double.valueOf(b);
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
                .whereArrayContains("Orders", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String waiterss = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("abc", document.getData().toString());
                                if (waiterss.equalsIgnoreCase(""))
                                    waiterss = document.get("name").toString();
                                else
                                    waiterss = waiterss + ", " + document.get("name").toString();

                            }
                            waiterslist.setText(waiterss);
                        } else {
                            Log.d("abc", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}
