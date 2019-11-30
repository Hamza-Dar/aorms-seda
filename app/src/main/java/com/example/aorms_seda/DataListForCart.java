package com.example.aorms_seda;
public class DataListForCart {
    String URI,itemName,id;
    private int Quantity;

    public DataListForCart(){

    }
    public DataListForCart(String id,String URI, String itemName) {
        this.URI = URI;
        this.id=id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
