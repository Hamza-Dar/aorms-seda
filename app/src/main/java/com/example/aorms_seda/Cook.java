package com.example.aorms_seda;

import java.util.ArrayList;

public class Cook {

    int cookId;
    String name;
    ArrayList<String> speciality;


    public Cook(int cookId, String name, ArrayList<String> Speciality) {
        cookId = cookId;
        this.name = name;
        speciality = Speciality;
    }

    public int getCookId() {
        return cookId;
    }

    public void setCookId(int cookId) {
        this.cookId = cookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSpeciality() {
        return speciality;
    }

    public void setSpeciality(ArrayList<String> speciality) {
        this.speciality = speciality;
    }
}


