package com.example.aorms_seda;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FoodItemDetails extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FoodItem f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_details);

        f =  (FoodItem) getIntent().getSerializableExtra("FoodItem");

        TextView id, name, time, type, price, available;
        id = findViewById(R.id.dishId);
        name = findViewById(R.id.dishName);
        time = findViewById(R.id.dishTime);
        type = findViewById(R.id.dishType);
        price = findViewById(R.id.dishPrice);
        available = findViewById(R.id.dishAvailable);
        final TextView ingredients = findViewById(R.id.dishIngredients);
        ingredients.setText("");

        id.setText("Dish ID: " + f.id);
        name.setText("Dish Name: " + f.name);
        type.setText("Dish Type: " + f.type);
        available.setText("Dish Availability: " + f.available);
        time.setText("Dish Time: " + f.time);
        price.setText("Dish Price: " + f.price);

        Log.d("FOODITEM DETAILS", f.type);
        db.collection("FooditemDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    String id = (String)documentSnapshot.getData().get("FooditemID");
                    Log.d("FOODITEM ID",  "" + id + "\t" + f.id);

                    if(id.equals(f.id)) {
                        ArrayList<Map> in = (ArrayList<Map>) documentSnapshot.getData().get("IngredientList");
                        Log.d("ARRAY SIZE", "" + in.size());
                        for (int i = 0; i < in.size(); i++) {
                            ingredients.setText(ingredients.getText().toString() + "Ingredient ID: " + in.get(i).get("IngredientID") + "\n");
                            ingredients.setText(ingredients.getText().toString() + "Quantity: " + in.get(i).get("Quantity") + "\n");
                        }
                        break;
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error: Database not accessible", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void UpdateDish(View view){

    }

}
