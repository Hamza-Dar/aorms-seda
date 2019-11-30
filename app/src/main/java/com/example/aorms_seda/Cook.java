package com.example.aorms_seda;

import java.util.ArrayList;

public class Cook {

    int cookId;
    String name;
    int workload;
    ArrayList<String> speciality;

    public Cook(int cookId, String name, int workload, ArrayList<String> speciality) {
        this.cookId = cookId;
        this.name = name;
        this.workload = workload;
        this.speciality = speciality;
    }

    public int getWorkload() {
        return workload;
    }


    public void incrementWorkLoad()
    {
        this.workload++;
    }

    public void decrementWorkLoad()
    {

        this.workload--;
        if(workload<0)
        {
            workload=0;
        }
    }

    public int getCookId() {
        return cookId;
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

}


