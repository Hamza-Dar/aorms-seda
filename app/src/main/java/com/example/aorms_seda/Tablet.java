package com.example.aorms_seda;

import java.io.Serializable;

public class Tablet implements Serializable {
    String id, model, purchaseDate;
    int regNo;

    public Tablet(String id, String model, String purchaseDate, int regNo) {
        this.id = id;
        this.model = model;
        this.purchaseDate = purchaseDate;
        this.regNo = regNo;
    }
}
