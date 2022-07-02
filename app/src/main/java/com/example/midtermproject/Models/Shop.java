package com.example.midtermproject.Models;

public class Shop {
    String id, name,location, Img;
    public Shop(){

    }

    public Shop(String name, String location, String img) {
        this.name = name;
        this.location = location;
        Img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }
}
