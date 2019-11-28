package com.example.aorms_seda;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class Employee implements Serializable {

    String name, id, address, emp_date, emp_type;
    int age;
    float salary;
    ArrayList<String> speciality;

    public Employee(String name, String id, String address, String emp_type, int age, float salary, ArrayList<String> speciality) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.emp_type = emp_type;
        this.age = age;
        this.salary = salary;
        this.speciality = speciality;
    }

    public Employee(String name, String id, String address, String emp_date, ArrayList<String> specialty, String emp_type, int age, float salary) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.emp_date = emp_date;
        this.speciality = specialty;
        this.emp_type = emp_type;
        this.age = age;
        this.salary = salary;
    }
}
