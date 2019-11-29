package com.example.aorms_seda;

public class OrderItem  {
    FoodItem item;
    int no;
    private int quantity;
    String status;

    public OrderItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        status = "waiting";
    }

    public String  getName() {
        return item.name;
    }

    public void setName(FoodItem item) {
        this.item.name = item.name;
    }

    public int  getPrice() {
        return item.price;
    }

    public void setPrice(FoodItem item) {
        this.item.price = item.price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
