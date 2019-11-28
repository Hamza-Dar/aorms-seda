package com.example.aorms_seda;
public class DataListForCart {
    String URI,itemName;
    private int Quantity;

    public DataListForCart(){

    }
    public DataListForCart(String URI, String itemName) {
        this.URI = URI;
        this.itemName = itemName;
        Quantity = 1;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

}
