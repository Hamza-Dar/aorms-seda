package com.example.aorms_seda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuAdd extends Activity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.8));
    }

    boolean validate(String name, String type, String uri, String available, String price, String time) {

        if (name.isEmpty() || type.isEmpty() || uri.isEmpty() || available.isEmpty() || price.isEmpty()){
            Toast.makeText(this, "Error: All fields must be filled", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.parseInt(price) < 0 || Integer.parseInt(time) < 0){
            Toast.makeText(this, "Error: Time & price MUST be greater than zero.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void AddDish(View view){
        EditText name, type, uri, available, price, time;
        name = (EditText) findViewById(R.id.edt_dishName);
        type = (EditText) findViewById(R.id.edt_dishType);
        uri = findViewById(R.id.edt_dishUri);
        available = (EditText) findViewById(R.id.edt_dishAvailable);
        price = (EditText) findViewById(R.id.edt_dishPrice);
        time = (EditText) findViewById(R.id.edt_dishTime);

        boolean success = validate(name.getText().toString(), type.getText().toString(), uri.getText().toString(),
                available.getText().toString(), price.getText().toString(), time.getText().toString());

        if(success){
            Map<String, Object> data = new HashMap<>();
            data.put("Name", name.getText().toString());
            data.put("Available", available.getText().toString());
            data.put("Type", type.getText().toString());
            data.put("Uri", uri.getText().toString());
            data.put("Price", Integer.parseInt(price.getText().toString()));
            data.put("Time", Integer.parseInt(time.getText().toString()));

            db.collection("Fooditem").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Toast.makeText(getApplication(), "Dish Added Successfully", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplication(), "Error: Database Not Accessible", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void Back(View view){
        finish();
    }

}
