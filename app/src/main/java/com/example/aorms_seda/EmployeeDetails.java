package com.example.aorms_seda;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeDetails extends AppCompatActivity {

    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        employee = (Employee) getIntent().getSerializableExtra("Employee");

        TextView name, id, address, age, emp_date, type, specaility, salary;
        name = findViewById(R.id.employee_name);
        id = findViewById(R.id.employee_id);;
        address = findViewById(R.id.employee_address);
        age = findViewById(R.id.employee_age);
        emp_date = findViewById(R.id.employee_date);
        type = findViewById(R.id.employee_type);
        specaility = findViewById(R.id.employee_Speciality);
        salary = findViewById(R.id.employee_salary);

        name.setText("Name: " + employee.name);
        id.setText("ID: " + employee.id);
        address.setText("Address: " + employee.address);
        age.setText("Age: " + employee.age);
        emp_date.setText("Employment Date: " + employee.emp_date);
        type.setText("Employee Type: " + employee.emp_type);
        salary.setText("Salary: Rs. " + employee.salary);

        if(employee.specialty.equals(""))
            specaility.setText("");
        else
            specaility.setText("Speciality: " + employee.specialty);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        //startActivity(new Intent(this, EmployeeOption.class));
        finish();
        return true;
    }
}
