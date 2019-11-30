package com.example.aorms_seda;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;

public class InventoryManager extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final ArrayList<Ingredient> ingredients = new ArrayList<>();
    ArrayList<String> docId = new ArrayList<>();

    RecyclerView recyclerView;
    boolean gotList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_manager);

        recyclerView = findViewById(R.id.recycler1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        getData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void getData(){
        gotList = false;
        ingredients.clear();
        docId.clear();
        CollectionReference ingredient = db.collection("Ingredient");
        ingredient.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {

                    String name =(String)documentSnapshot.getData().get("Name");
                    int price = Math.toIntExact(documentSnapshot.getLong("Price"));
                    int quantity = Math.toIntExact(documentSnapshot.getLong("Quantity"));
                    int threshold = Math.toIntExact(documentSnapshot.getLong("Threshold"));
                    Log.d("INGREDIENTS", name + "\t" + price + "\t" + quantity + "\t" + threshold);
                    ingredients.add(new Ingredient(name, documentSnapshot.getId(), price, quantity, threshold));

                    recyclerView.setAdapter(new IngredientAdapter(ingredients));
                    docId.add(documentSnapshot.getId());
                    gotList = true;
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void AddIngredient(View view){
        if(gotList) {
            Intent i = new Intent(this, IngredientAdd.class);
            startActivityForResult(i, 1);
        }
        else{
            Toast.makeText(this, "Database not accessed", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getData();
    }

    public void UpdateIngredient(View view){
        if(gotList) {
            Intent i = new Intent(this, IngredientUpdate.class);
            i.putStringArrayListExtra("docId", docId);
            startActivityForResult(i, 1);
        }
        else{
            Toast.makeText(this, "Database not accessed", Toast.LENGTH_LONG).show();
        }
    }

}
