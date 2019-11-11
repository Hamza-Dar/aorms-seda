package com.example.aorms_seda;
import java.io.Serializable;

public class Ingredient implements Serializable {

    String name, id;
    int quantity, threshold;

    public Ingredient(String name, String id, int quantity, int threshold) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.threshold = threshold;
    }
}
