package com.example.aorms_seda;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class FoodItem implements Serializable {
    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id, name, available, type, uri;
    int time, price;

    public FoodItem(String id, String name, String available, String type, String uri, int time, int price) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.type = type;
        this.uri = uri;
        this.time = time;
        this.price = price;
    }

    public FoodItem(String name, String id, String available, int price, int time) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.price = price;
        this.time = time;
    }
}
