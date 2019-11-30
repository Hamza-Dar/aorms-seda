package com.example.aorms_seda;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MenuOption extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<FoodItem> items = new ArrayList<>();
    ArrayList<String> docId = new ArrayList<>();
    boolean gotList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_option);

        recyclerView = findViewById(R.id.recycler2);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        BottomNavigationView bottomNav=findViewById(R.id.menuBottomnav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void getData(){
        gotList = false;
        items.clear();
        docId.clear();
        CollectionReference menu = db.collection("Fooditem");
        menu.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {

                    String name =(String)documentSnapshot.getData().get("Name");
                    String uri =(String)documentSnapshot.getData().get("Uri");
                    String type =(String)documentSnapshot.getData().get("Type");
                    String id = documentSnapshot.getId();
                    String available = (String)documentSnapshot.getData().get("Available");
                    int price = Math.toIntExact(documentSnapshot.getLong("Price"));
                    int time = Math.toIntExact(documentSnapshot.getLong("Time"));

                    items.add(new FoodItem(id, name, available, type, uri, price, time));
                    recyclerView.setAdapter(new MenuAdapter(items));
                    docId.add(documentSnapshot.getId());
                    gotList = true;
                }
            }
        });
        recyclerView.setAdapter(new MenuAdapter(items));
    }

    public void AddMenuButton(View view){
        if(gotList) {
            Intent i = new Intent(this, MenuAdd.class);
            i.putStringArrayListExtra("docId", docId);
            startActivity(i);
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

    public void RemoveMenuButton(View view){
        if(gotList) {
            Intent i = new Intent(this, MenuRemove.class);
            i.putStringArrayListExtra("docId", docId);
            startActivity(i);
            startActivityForResult(i, 1);
        }
        else{
            Toast.makeText(this, "Database not accessed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_menu:
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You are already in this Activity",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case R.id.nav_tablets:
                    startActivity(new Intent(getApplicationContext(), TabletOption.class));
                    finish();
                    break;
                case R.id.nav_employees:
                    startActivity(new Intent(getApplicationContext(), EmployeeOption.class));
                    finish();
                    break;

            }

            return true;
        }
    };

}
