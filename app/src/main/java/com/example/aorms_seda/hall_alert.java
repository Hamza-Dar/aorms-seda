package com.example.aorms_seda;

import com.google.firebase.firestore.PropertyName;

public class hall_alert {
    @PropertyName("Order")
    String order;
    @PropertyName("Change")
    String change;

    public hall_alert() {
    }


    public hall_alert(String order, String change) {
        this.order = order;
        this.change = change;
    }
    @PropertyName("Order")
    public String getOrder() {
        return order;
    }
    @PropertyName("Order")
    public void setOrder(String order) {
        this.order = order;
    }
    @PropertyName("Change")
    public String getChange() {
        return change;
    }
    @PropertyName("Change")
    public void setChange(String change) {
        this.change = change;
    }
}
