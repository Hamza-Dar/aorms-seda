package com.example.aorms_seda;

import com.google.firebase.firestore.PropertyName;

public class hall_threshold {
    @PropertyName("ID")
    int id;
    @PropertyName("Name")
    String name;
    @PropertyName("Threshold")
    int threshold;

    public hall_threshold() {
    }

    public hall_threshold(int id, String name, int threshold) {
        this.id = id;
        this.name = name;
        this.threshold = threshold;
    }
    @PropertyName("ID")
    public int getId() {
        return id;
    }
    @PropertyName("ID")
    public void setId(int id) {
        this.id = id;
    }
    @PropertyName("Name")
    public String getName() {
        return name;
    }
    @PropertyName("Name")
    public void setName(String name) {
        this.name = name;
    }
    @PropertyName("Threshold")
    public int getThreshold() {
        return threshold;
    }
    @PropertyName("Threshold")
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
