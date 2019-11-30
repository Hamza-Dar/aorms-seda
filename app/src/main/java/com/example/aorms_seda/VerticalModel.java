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

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Dish> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Dish> arrayList) {
        this.arrayList = arrayList;
    }
}

