package com.example.aorms_seda;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Tablet implements Serializable {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String id, model, purchaseDate;
    int regNo;


    public Tablet(String id, String model, String purchaseDate, int regNo) {
        this.id = id;
        this.model = model;
        this.purchaseDate = purchaseDate;
        this.regNo = regNo;
    }
}
