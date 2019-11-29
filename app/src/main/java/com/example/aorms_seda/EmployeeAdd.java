package com.example.aorms_seda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeAdd extends Activity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.8));
    }

    boolean validate(String name, String address, String age, String job, String salary, String speciality) {

        ArrayList<String> s = new ArrayList<>();
        if(job.equalsIgnoreCase("Cook") && speciality.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Error: Specilaity MUST be added for Cook", Toast.LENGTH_LONG).show();
        }
        else if(job.equalsIgnoreCase("Cook") && !speciality.isEmpty()){
            s.add(speciality);
        }

        if (name.isEmpty() || address.isEmpty() || age.isEmpty() || job.isEmpty() || salary.isEmpty()){
            Toast.makeText(this, "Error: All fields must be filled", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.parseInt(age) < 0 || Integer.parseInt(salary) < 0){
            Toast.makeText(this, "Error: Age, & salary MUST be greater than zero.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    ArrayList<String> s = new ArrayList<>();

    public void AddEmployee(View view){
        final EditText name, address, age, job, salary, speciality;
        name = (EditText) findViewById(R.id.edt_empName);
        address = findViewById(R.id.edt_empAddress);
        age = (EditText) findViewById(R.id.edt_empAge);
        job = (EditText) findViewById(R.id.edt_empJob);
        salary = (EditText) findViewById(R.id.edt_empSalary);
        speciality = findViewById(R.id.edt_empSpeciality);

        boolean success = validate(name.getText().toString(), address.getText().toString(),
                age.getText().toString(), job.getText().toString(), salary.getText().toString(), speciality.getText().toString());

        if(success){
            Map<String, Object> data = new HashMap<>();
            data.put("Name", name.getText().toString());
            data.put("Address", address.getText().toString());
            data.put("Job", job.getText().toString());
            data.put("Salary", Integer.parseInt(salary.getText().toString()));
            data.put("Age", Integer.parseInt(age.getText().toString()));
            data.put("speciality", s);

            db.collection("Employee").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getApplication(), "Employee Added Successfully", Toast.LENGTH_LONG).show();
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
