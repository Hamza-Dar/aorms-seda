package com.example.aorms_seda;

import com.google.firebase.firestore.PropertyName;

//Hall Manager module MODEL CLASS
public class hall_manager_new_order_list_item {
    // public String Items;  Yet to be implemented
    @PropertyName("Status")
    public String status;
    @PropertyName("Table")
    public int table;
    @PropertyName("Time")
    public int time;
    public hall_manager_new_order_list_item(){
        //Empty constructor needed DO NOT DELETE
    }
    public hall_manager_new_order_list_item( String status, int table, int time) {

        this.status = status;
        this.table = table;
        this.time = time;
    }
    @PropertyName("Status")
    public void setStatus(String status) {
        this.status = status;
    }
    @PropertyName("Table")
    public void setTable(int table) {
        this.table = table;
    }
    @PropertyName("Time")
    public void setTime(int time) {
        this.time = time;
    }
    @PropertyName("Status")
    public String getStatus() {
        return status;
    }
    @PropertyName("Table")
    public int getTable() {
        return table;
    }
    @PropertyName("Time")
    public int getTime() {
        return time;
    }
}
