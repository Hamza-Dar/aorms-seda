package com.example.aorms_seda;

public class Bill {
    int price;
    String OrderID;
    String Date;
    public Bill(int price, String orderID, String Date) {
        this.price = price;
        OrderID = orderID;
        this.Date = Date;
    }
}
