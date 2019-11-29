package com.example.aorms_seda;

import java.io.Serializable;

public class RequestsChange implements Serializable {

    Dish dishInfo;
    String request;
    String decision;
    int orderId;

    public RequestsChange(Dish dishInfo, String request, String decision, int orderId) {
        this.dishInfo = dishInfo;
        this.request = request;
        this.decision = decision;
        this.orderId = orderId;
    }

    public Dish getDishInfo() {
        return dishInfo;
    }

    public void setDishInfo(Dish dishInfo) {
        this.dishInfo = dishInfo;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
