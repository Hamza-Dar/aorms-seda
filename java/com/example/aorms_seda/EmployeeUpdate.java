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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeUpdate extends Activity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_update);

        employee = (Employee) getIntent().getSerializableExtra("Employee");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.9));
    }

    boolean validate(String name, String address, String age, String salary, String speciality, String job) {

        if(job.equalsIgnoreCase("Cook") && speciality.isEmpty()){
            Toast.makeText(this, "Error: Speciality MUST be added for a Cook", Toast.LENGTH_LONG).show();
            return false;
        }

        if (name.isEmpty() || address.isEmpty() || age.isEmpty() || salary.isEmpty() || job.isEmpty()){
            Toast.makeText(this, "Error: All fields must be filled", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.parseInt(age) < 0 || Integer.parseInt(salary) < 0){
            Toast.makeText(this, "Error: Age & Salary MUST be greater than zero.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void UpdateButton(View view){
        EditText name, id, address, age, salary, speciality, job;
        name = (EditText) findViewById(R.id.edtEmpName);
        address = findViewById(R.id.edtEmpAddress);
        age = (EditText) findViewById(R.id.edtEmpAge);
        salary = (EditText) findViewById(R.id.edtEmpSalary);
        speciality = (EditText) findViewById(R.id.edtEmpSpeciality);
        job = (EditText) findViewById(R.id.edtEmpJob);

        boolean success;
        success = validate(name.getText().toString(), address.getText().toString(),
                age.getText().toString(), salary.getText().toString(), speciality.getText().toString(), job.getText().toString());

        if(success){
            final Map<String, Object> data = new HashMap<>();
            data.put("Name", name.getText().toString());
            data.put("Address", address.getText().toString());
            final ArrayList<String> s = new ArrayList<>();
            s.add(speciality.getText().toString());
            data.put("speciality", s);
            data.put("Job", job.getText().toString());
            data.put("Age", Integer.parseInt(age.getText().toString()));
            data.put("Salary", Integer.parseInt(salary.getText().toString()));

            db.collection("Employee").document(employee.id).update(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            employee.name = (String)data.get("Name");
                            employee.address = (String)data.get("Address");
                            employee.emp_type = (String)data.get("Job");
                            employee.age = (int)data.get("Age");
                            employee.salary = (int)data.get("Salary");
                            employee.speciality = s;
                            EmployeeDetails.updateEmployee(employee);

                            Toast.makeText(getApplicationContext(), "Employee Updated Successfully", Toast.LENGTH_LONG).show();
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
