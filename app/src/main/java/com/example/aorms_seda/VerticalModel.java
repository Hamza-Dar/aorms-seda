package com.example.aorms_seda;

import java.io.Serializable;
import java.util.ArrayList;

public class VerticalModel implements Serializable {


    String title;
    ArrayList<Dish> arrayList;
    public VerticalModel(String title, ArrayList<Dish> arrayList) {
        this.title = title;
        this.arrayList = arrayList;
    }
    public String getTitle() {
        return title;
    }
    public ArrayList<Dish> getArrayList() {
        return arrayList;
    }

}

