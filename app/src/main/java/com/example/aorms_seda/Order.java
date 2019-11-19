package com.example.aorms_seda;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    String orderId;
    int serveTime;
    ArrayList<Dish> dishes;  //list of dishes
    String status;
    int table;


    public Order(String orderId, int serveTime, ArrayList<Dish> dishes, String status, int tableNo) {
        this.orderId = orderId;
        this.serveTime = serveTime;
        this.dishes = dishes;
        this.status = status;
        this.table = tableNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public int getServeTime() {
        return serveTime;
    }

    public void setServeTime(int serveTime) {
        this.serveTime = serveTime;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes)
    {
        this.dishes=dishes;
    }
    public void addDish(Dish dish)
    {
        if (this.dishes==null)
        {
            dishes=new ArrayList<>();
        }
        dishes.add(new Dish(orderId,dish.dishId,dish.name,dish.cookingTime,dish.cookId,dish.status,dish.category));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
