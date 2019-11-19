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

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Dish(int dishId, String name, int cookingTime, String status, String category, int dishTime) {
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public int getCookId() {
        return cookId;
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

    public void setCategory(String category) {
        this.category = category;
    }
}
