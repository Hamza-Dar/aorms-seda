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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TabletOption extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<Tablet> tablets = new ArrayList<>();
    boolean gotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet_option);

        BottomNavigationView bottomNav = findViewById(R.id.TabletBottomnav);
        bottomNav.setOnNavigationItemSelectedListener(navListenerTablet);

        recyclerView = findViewById(R.id.recycler3);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        getData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    void getData(){
        gotList = false;
        tablets.clear();
        CollectionReference menu = db.collection("Tablet");
        menu.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    String model =(String)documentSnapshot.getData().get("Model");
                    int regNo = Math.toIntExact(documentSnapshot.getLong("RegNo"));
                    int table = Math.toIntExact(documentSnapshot.getLong("Table"));
                    String date = (String)documentSnapshot.getData().get("PurchaseDate");
                    //Timestamp date = (Timestamp) documentSnapshot.getTimestamp("PurchaseDate");

                    tablets.add(new Tablet(documentSnapshot.getId(), model, date, table));

                    recyclerView.setAdapter(new TabletAdapter(tablets));
                    gotList = true;
                }
            }
        });
    }

    public void AddTablet(View view){
        if(gotList) {
            Intent i = new Intent(this, TabletAdd.class);
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

    public void RemoveTablet(View view){
        if(gotList) {
            Intent i = new Intent(this, TabletRemove.class);
            startActivity(i);
            startActivityForResult(i, 1);
        }
        else{
            Toast.makeText(this, "Database not accessed", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onSupportNavigateUp(){
        // startActivity(new Intent(this, AdminOptions.class));
        finish();
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListenerTablet = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_menu:
                    startActivity(new Intent(getApplicationContext(), MenuOption.class));
                    finish();
                    break;
                case R.id.nav_tablets:
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You are already in this Activity",
                            Toast.LENGTH_SHORT);
                    toast.show();
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
