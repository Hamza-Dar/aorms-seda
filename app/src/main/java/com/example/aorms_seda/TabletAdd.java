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

import java.util.HashMap;
import java.util.Map;

public class TabletAdd extends Activity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet_add);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.8));
    }

    boolean validate(String model, String regNo, String date, String table) {

        if (model.isEmpty() || regNo.isEmpty() || date.isEmpty() || table.isEmpty()){
            Toast.makeText(this, "Error: All fields must be filled", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.parseInt(regNo) < 0 || Integer.parseInt(table) < 0 ){
            Toast.makeText(this, "Error: Registration Number& Table MUST be greater than zero.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void AddTablet(View view){
        final EditText model, regNo, date, table;
        model = (EditText) findViewById(R.id.edt_tabletModel);
        regNo = (EditText) findViewById(R.id.edt_tabletRegNo);
        date = findViewById(R.id.edt_tabletpurchaseDate);
        table = (EditText) findViewById(R.id.edt_tabletTable);

        boolean success = validate(model.getText().toString(), regNo.getText().toString(), date.getText().toString(),
                table.getText().toString());

        if(success){
            Map<String, Object> data = new HashMap<>();
            data.put("Model", model.getText().toString());
            data.put("PurchaseDate", date.getText().toString());
            data.put("RegNo", Integer.parseInt(regNo.getText().toString()));
            data.put("Table", Integer.parseInt(table.getText().toString()));

            db.collection("Tablet").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
