package com.example.aorms_seda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

public class IngredientUpdate extends Activity {

    ArrayList<String> docID = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_update);

        docID = getIntent().getStringArrayListExtra("docId");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.8));

    }

    boolean validate(String name, String id, String quantity, String threshold, String price) {

        if (name.isEmpty() || id.isEmpty() || quantity.isEmpty() || threshold.isEmpty() || price.isEmpty()){
            Toast.makeText(this, "Error: All fields must be filled", Toast.LENGTH_LONG).show();
            return false;
        }

        boolean found = false;
        for(int i = 0; i < docID.size(); i++){
            if(id.equals(docID.get(i))) {
                index = i;
                found = true;
                break;
            }
        }
        if(!found){
            Toast.makeText(this, "Error: No ingredient with this ID", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.parseInt(quantity) < 0 || Integer.parseInt(threshold) < 0 || Double.parseDouble(price) < 0){
            Toast.makeText(this, "Error: Quantity, Threshold & price MUST be greater than zero.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void UpdateButton(View view){
        EditText name, id, quantity, threshold, price;
        id = (EditText) findViewById(R.id.edt0);
        name = (EditText) findViewById(R.id.edt1);
        quantity = findViewById(R.id.edt2);
        threshold = (EditText) findViewById(R.id.edt3);
        price = (EditText) findViewById(R.id.edt4);

        boolean success;
        success = validate(name.getText().toString(), id.getText().toString(), quantity.getText().toString(),
                threshold.getText().toString(), price.getText().toString());

        if(success){
            Map<String, Object> data = new HashMap<>();
            data.put("Name", name.getText().toString());
            data.put("Price", Double.parseDouble(price.getText().toString()));
            data.put("Quantity", Integer.parseInt(quantity.getText().toString()));
            data.put("Threshold", Integer.parseInt(threshold.getText().toString()));

            db.collection("Ingredient").document(docID.get(index)).update(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Ingredient Updated Successfully", Toast.LENGTH_LONG).show();
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
