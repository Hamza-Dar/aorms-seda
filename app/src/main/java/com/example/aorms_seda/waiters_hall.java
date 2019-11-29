package com.example.aorms_seda;

import com.google.firebase.firestore.PropertyName;

public class waiters_hall {
    @PropertyName("Order")
    String order;
    @PropertyName("name")
    String name;
    @PropertyName("status")
    String status;

    public waiters_hall(){}

    public waiters_hall(String order, String name, String status) {
        this.order = order;
        this.name = name;
        this.status = status;
    }
    @PropertyName("Order")
    public String getOrder() {
        return order;
    }
    @PropertyName("name")
    public String getName() {
        return name;
    }
    @PropertyName("status")
    public String getStatus() {
        return status;
    }
    @PropertyName("Order")
    public void setOrder(String order) {
        this.order = order;
    }
    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }
    @PropertyName("status")
    public void setStatus(String status) {
        this.status = status;
    }
}
