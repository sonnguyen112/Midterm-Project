package com.example.midtermproject.Models;

public class Good {
    String name, goodImg;
    int amount;
    Long price;

    public Good(){}

    public Good(String name, int amount, Long price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public String getGoodImg() {
        return goodImg;
    }

    public void setGoodImg(String goodImg) {
        this.goodImg = goodImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
