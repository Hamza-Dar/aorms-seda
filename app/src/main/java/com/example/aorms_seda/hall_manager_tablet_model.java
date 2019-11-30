package com.example.aorms_seda;
import com.google.firebase.firestore.PropertyName;


public class hall_manager_tablet_model {
    @PropertyName("Model")
    String model;
    @PropertyName("RegNo")
    int reg;
    @PropertyName("Table")
    int table;


    public hall_manager_tablet_model() {

    }

    public hall_manager_tablet_model(String model, int reg, int table) {
        this.model = model;
        this.reg = reg;
        this.table = table;
    }
    @PropertyName("Model")
    public void setModel(String model) {
        this.model = model;
    }
    @PropertyName("RegNo")
    public void setReg(int reg) {
        this.reg = reg;
    }
    @PropertyName("Table")
    public void setTable(int table) {
        this.table = table;
    }
    @PropertyName("Model")
    public String getModel() {
        return model;
    }
    @PropertyName("RegNo")
    public int getReg() {
        return reg;
    }
    @PropertyName("Table")
    public int getTable() {
        return table;
    }
}
