package com.example.aorms_seda;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Employee implements Serializable {
 //   FirebaseFirestore db = FirebaseFirestore.getInstance();
    String name, id, address, emp_date, specialty, emp_type;
    int age;
    float salary;

    public Employee(String name, String id, String address, String emp_date, String specialty, String emp_type, int age, float salary) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.emp_date = emp_date;
        this.specialty = specialty;
        this.emp_type = emp_type;
        this.age = age;
        this.salary = salary;
    }
}
