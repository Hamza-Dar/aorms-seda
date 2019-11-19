package com.example.aorms_seda;

import java.io.Serializable;

public class RequestsChange implements Serializable {

    String dishInfo;
    String status;
    String request;
    String decision;
    String orderId;

    public RequestsChange(String dishInfo, String request, String decision, String orderId,String status) {
        this.dishInfo = dishInfo;
        this.request = request;
        this.decision = decision;
        this.orderId = orderId;
        this.status=status;
    }

    public String getDishInfo() {
        return dishInfo;
    }

    public void setDishInfo(String dishInfo) {
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
