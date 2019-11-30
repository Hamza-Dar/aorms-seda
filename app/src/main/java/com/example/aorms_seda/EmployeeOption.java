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

public class EmployeeOption extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Employee> emps = new ArrayList<>();
    static ArrayList<String> docId = new ArrayList<>();

    RecyclerView recyclerView;
    boolean gotList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_option);

        BottomNavigationView bottomNav = findViewById(R.id.employeeBottomnav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        getData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    static ArrayList<String> getIds(){
        return docId;
    }

    void getData(){
        gotList = false;
        emps.clear();
        docId.clear();
        CollectionReference employees = db.collection("Employee");
        employees.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    String name =(String)documentSnapshot.getData().get("Name");
                    String job =(String)documentSnapshot.getData().get("Job");
                    String address =(String)documentSnapshot.getData().get("Address");

                    int age = Math.toIntExact(documentSnapshot.getLong("Age"));
                    int salary = Math.toIntExact(documentSnapshot.getLong("Salary"));

                    ArrayList<String> speciality = null;
                    if(job.equalsIgnoreCase("Cook"))
                        speciality = (ArrayList<String>) documentSnapshot.getData().get("speciality");

                    emps.add(new Employee(name, documentSnapshot.getId(), address, job, age, salary, speciality));

                    docId.add(documentSnapshot.getId());
                    gotList = true;
                }
                recyclerView.setAdapter(new EmployeeAdapter(emps));
            }
        });
    }

    public void AddEmployee(View view){
        if(gotList) {
            Intent i = new Intent(this, EmployeeAdd.class);
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

    public void RemoveEmployee(View view){
        if(gotList) {
            Intent i = new Intent(this, EmployeeRemove.class);
            startActivityForResult(i, 1);
        }
        else{
            Toast.makeText(this, "Database not accessed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
       // startActivity(new Intent(this, AdminOptions.class));
        finish();
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_menu:
                    startActivity(new Intent(getApplicationContext(), MenuOption.class));
                    finish();
                    break;
                case R.id.nav_tablets:
                    startActivity(new Intent(getApplicationContext(), TabletOption.class));
                    finish();
                    break;
                case R.id.nav_employees:
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You are already in this Activity",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    break;

            }
            return true;
        }
    };

}
