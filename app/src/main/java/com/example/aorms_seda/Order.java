package com.example.aorms_seda;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    int orderId;
    String serveTime;
    ArrayList<Dish> dishes;  //list of dishes
    String status;

    public Order(int orderId, String serveTime, ArrayList<Dish> dishes, String status) {
        this.orderId = orderId;
        this.serveTime = serveTime;
        this.dishes = dishes;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getServeTime() {
        return serveTime;
    }

    public void setServeTime(String serveTime) {
        this.serveTime = serveTime;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
