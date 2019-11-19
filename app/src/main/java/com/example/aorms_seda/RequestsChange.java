package com.example.aorms_seda;

import java.io.Serializable;

public class RequestsChange implements Serializable {

    String requestID;
    String dishID;
    String dishInfo;
    String status;
    String request;
    String decision;
    String orderId;

    public RequestsChange(String dishInfo, String request, String decision, String orderId,String status,String dishID,String requestID) {
        this.dishInfo = dishInfo;
        this.request = request;
        this.decision = decision;
        this.orderId = orderId;
        this.status=status;
        this.dishID=dishID;
        this.requestID=requestID;
    }

    public String getRequest() {
        return request;
    }

}
