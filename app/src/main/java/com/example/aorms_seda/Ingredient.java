package com.example.aorms_seda;


public class Ingredient {

    String name, id;
    int quantity, threshold;

    public Ingredient(String name, String id, int quantity, int threshold) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient(String name) {
        this.name = name;
    }
}
