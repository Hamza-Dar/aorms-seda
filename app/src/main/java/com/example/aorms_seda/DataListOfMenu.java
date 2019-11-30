package com.example.aorms_seda;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DataListOfMenu {
    private String id,Available, Name,Type,Uri;
    private int Price,Time;

    public DataListOfMenu(String id, String Available, String name, Number price, Number time, String type, String uri) {
        this.id = id;
        this.Available = Available;
        this.Name = name;
        this.Price = (int)(price);
        this.Time = (int)time;
        this.Type = type;
        this.Uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataListOfMenu() {
    }

    public String getAvailable() {
        return Available;
    }

    public void setAvailable(String Available) {
        this.Available = Available;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Number getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        this.Price = price;
    }

    public Number getTime() {
        return Time;
    }

    public void setTime(int time) {
        this.Time = time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String tyepe) {
        this.Type = tyepe;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        this.Uri = uri;
    }
}
