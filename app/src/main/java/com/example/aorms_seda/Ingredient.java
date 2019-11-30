package com.example.aorms_seda;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Ingredient implements Serializable {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String name, id;
    int quantity, threshold;
    double price;


    public Ingredient(String name, String id, double price, int quantity, int threshold) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.threshold = threshold;
    }

}
