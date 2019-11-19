package com.example.aorms_seda;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EmployeeOption extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_option);

        Employee e1 = new Employee("Tayyab", "0001", "Fast nuces, Lahore", "10/1/2018", "Fast Food", "Chef", 22, 15000.0f);
        Employee e2 = new Employee("Ali", "0002", "Fast nuces, Lahore", "10/1/2018", "", "Waiter", 22, 15000.0f);
        Employee e3 = new Employee("Xtylish", "0003", "Fast nuces, Lahore", "10/1/2018", "", "Inventory Manager", 22, 15000.0f);
        Employee e4 = new Employee("King", "0004", "Fast nuces, Lahore", "10/1/2018", "", "Kitchen Manager", 22, 15000.0f);
        Employee e5 = new Employee("Hasan", "0005", "Fast nuces, Lahore", "10/1/2018", "", "Hall Manager", 22, 15000.0f);
        Employee e6 = new Employee("Sarwar", "0006", "Fast nuces, Lahore", "10/1/2018", "", "Waiter", 22, 15000.0f);

        ArrayList<Employee> emps = new ArrayList<>();
        emps.add(e1);
        emps.add(e2);
        emps.add(e3);
        emps.add(e4);
        emps.add(e5);
        emps.add(e6);

        RecyclerView recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(new EmployeeAdapter(emps));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
       // startActivity(new Intent(this, AdminOptions.class));
        finish();
        return true;
    }

}
