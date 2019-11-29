package com.example.aorms_seda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class hallthreshold extends AppCompatActivity {
    ImageView imageminus;
    ImageView imageplus;
    TextView textView;
    String threshold;
    String orderno;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hallthreshold);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> message=new ArrayList<>();
        message = bundle.getStringArrayList("message");
        orderno=message.get(0);
        threshold = message.get(1);
        textView=findViewById(R.id.idProductQty);
        imageminus=findViewById(R.id.idMinusICon);
        imageplus=findViewById(R.id.idPlusIcon);

        textView.setText(threshold+"Kg");
        imageminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x= Integer.parseInt(threshold);
                x--;
                threshold=String.valueOf(x);
                textView.setText(threshold+"Kg");
                Log.d("image","Heelo");
            }
        });

        imageplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x= Integer.parseInt(threshold);
                x++;
                threshold=String.valueOf(x);
                textView.setText(threshold+"Kg");
                Log.d("image","Heelo");
            }
        });
    }
    public void update(View v){
        if (Integer.valueOf(threshold)>=0) {

            Map<String, Object> waiter = new HashMap<>();
            waiter.put("Threshold", Integer.valueOf(threshold));
            //waiter.put("Order", message);
            DocumentReference washingtonRef = db.collection("Ingredient").document(orderno);
            db.collection("Ingredient").document(orderno)
                    .set(waiter, SetOptions.mergeFields("Threshold"))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Moiz", "DocumentSnapshot successfully written!");
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("sere", "Error writing document", e);
                        }
                    });
        }
        else {
            Toast.makeText(this,"Value Must Be Positive",Toast.LENGTH_SHORT).show();
        }


    }
}
