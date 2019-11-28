package com.example.aorms_seda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IngredientAdd extends Activity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_add);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.8));
    }

    boolean validate(String name, String quantity, String threshold, String price) {

        if (name.isEmpty() || quantity.isEmpty() || threshold.isEmpty() || price.isEmpty()){
            Toast.makeText(this, "Error: All fields must be filled", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.parseInt(quantity) < 0 || Integer.parseInt(threshold) < 0 || Double.parseDouble(price) < 0){
            Toast.makeText(this, "Error: Quantity, Threshold & price MUST be greater than zero.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void AddButton(View view){
        final EditText name, quantity, threshold, price;
        name = (EditText) findViewById(R.id.edt_ingredientName);
        quantity = findViewById(R.id.edt_ingredientQuantity);
        threshold = (EditText) findViewById(R.id.edt_ingredientThreshold);
        price = (EditText) findViewById(R.id.edt_ingredientPrice);

        boolean success = validate(name.getText().toString(), quantity.getText().toString(),
                threshold.getText().toString(), price.getText().toString());

        if(success){
            Map<String, Object> data = new HashMap<>();
            data.put("Name", name.getText().toString());
            data.put("Price", Double.parseDouble(price.getText().toString()));
            data.put("Quantity", Integer.parseInt(quantity.getText().toString()));
            data.put("Threshold", Integer.parseInt(threshold.getText().toString()));

            db.collection("Ingredient").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getApplication(), "Ingredient Added Successfully", Toast.LENGTH_LONG).show();
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
