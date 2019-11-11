package com.fast.tiffan_project;

public class DataListOfMenu {
    public String URI,menu_title,menu_description;
    public int price;

    public DataListOfMenu(String URI, String menu_title, String menu_description,int price) {
        this.URI = URI;
        this.price=price;
        this.menu_title = menu_title;
        this.menu_description = menu_description;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMenu_title() {
        return menu_title;
    }

    public void setMenu_title(String menu_title) {
        this.menu_title = menu_title;
    }

    public String getMenu_description() {
        return menu_description;
    }

    public void setMenu_description(String menu_description) {
        this.menu_description = menu_description;
    }
}
