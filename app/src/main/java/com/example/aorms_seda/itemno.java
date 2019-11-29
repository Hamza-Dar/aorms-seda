package com.example.aorms_seda;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.util.Map;

public class itemno implements Serializable {
    @PropertyName("foodItems")
    public Reference fooditems;
    @PropertyName("itemStatus")
    public String itemstatus;


    public itemno(Reference fooditems, String itemstatus) {
        this.fooditems = fooditems;
        this.itemstatus = itemstatus;
    }

    public itemno() {
    }
    @PropertyName("foodItems")
    public Reference getFooditems() {
        return fooditems;
    }
    @PropertyName("foodItems")
    public void setFooditems(Reference fooditems) {
        this.fooditems = fooditems;
    }
    @PropertyName("itemStatus")
    public String getItemstatus() {
        return itemstatus;
    }
    @PropertyName("itemStatus")
    public void setItemstatus(String itemstatus) {
        this.itemstatus = itemstatus;
    }
}
