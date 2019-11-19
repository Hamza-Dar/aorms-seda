package com.example.aorms_seda;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Ingredient implements Serializable {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String name, id;
    int quantity, threshold;

    public Ingredient(String name, String id, int quantity, int threshold) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.threshold = threshold;
    }
}
