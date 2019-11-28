package com.example.aorms_seda;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeDetails extends AppCompatActivity {

    static private Employee employee;

    public static void updateEmployee(Employee emp){
        employee = emp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        employee = (Employee) getIntent().getSerializableExtra("Employee");

        getData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void getData(){
        TextView name, id, address, age, emp_date, type, specaility, salary;
        name = findViewById(R.id.employee_name);
        id = findViewById(R.id.employee_id);;
        address = findViewById(R.id.employee_address);
        age = findViewById(R.id.employee_age);
        type = findViewById(R.id.employee_type);
        specaility = findViewById(R.id.employee_Speciality);
        salary = findViewById(R.id.employee_salary);

        name.setText("Name: " + employee.name);
        id.setText("ID: " + employee.id);
        address.setText("Address: " + employee.address);
        age.setText("Age: " + employee.age);
        type.setText("Employee Type: " + employee.emp_type);
        salary.setText("Salary: Rs. " + employee.salary);

        if(employee.speciality == null)
            specaility.setText("");
        else {
            specaility.setText("Speciality: ");
            for(int i = 0; i < employee.speciality.size(); i++) {
                specaility.setText(specaility.getText() + employee.speciality.get(i) + "\n");
            }
        }
    }

    public void UpdateEmployee(View view){
        Intent i = new Intent(this, EmployeeUpdate.class);
        i.putExtra("Employee", employee);
        startActivityForResult(i, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getData();
    }

    @Override
    public boolean onSupportNavigateUp(){
        //startActivity(new Intent(this, EmployeeOption.class));
        finish();
        return true;
    }
}
