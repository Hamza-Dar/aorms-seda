package com.example.aorms_seda;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class hall_order implements Serializable {
    // public String Items;  Yet to be implemented
    @PropertyName("Status")
    public String status;
    @PropertyName("Items")
    public List<Map<String,Object>> items;



    public hall_order(){
        //Empty constructor needed DO NOT DELETE
    }

    public hall_order(String status, List<Map<String, Object>> items) {
        this.status = status;
        this.items = items;
    }

    @PropertyName("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    @PropertyName("Status")
    public String getStatus() {
        return status;
    }

    @PropertyName("Items")
    public  List<Map<String,Object>> getItems() {
        return items;
    }
    @PropertyName("Items")
    public void setItems( List<Map<String,Object>> items) {
        this.items = items;
    }
}

