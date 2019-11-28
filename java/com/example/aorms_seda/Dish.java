package com.example.aorms_seda;

import java.io.Serializable;
import java.util.ArrayList;

public class Dish implements Serializable {

    String orderId;
    String dishId;
    String name;
    int cookingTime;
    int cookId;
    String status;
    String category;

    public Dish(String orderId, String dishId, String name, int cookingTime, int cookId, String status, String category) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.name = name;
        this.cookingTime = cookingTime;
        this.cookId = cookId;
        this.status = status;
        this.category = category;
    }

    public String getOrderId() {
        return orderId;
    }


    public String getDishId() {
        return dishId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCookId(int cookId) {
        this.cookId = cookId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }


}
