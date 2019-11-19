package com.example.aorms_seda;

import java.io.Serializable;

public class Dish implements Serializable {

    int dishId;
    String name;
    String cookingTime;
    String cookId;
    String status;
    String category;


    public Dish(int dishId, String name, String cookingTime, String cookId, String status, String category) {
        this.dishId = dishId;
        this.name = name;
        this.cookingTime = cookingTime;
        this.cookId = cookId;
        this.status = status;
        this.category = category;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getCookId() {
        return cookId;
    }

    public void setCookId(String cookId) {
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
