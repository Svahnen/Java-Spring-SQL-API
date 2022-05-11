package com.temperature.templog.models;

import java.time.LocalDateTime;

public class Price {
    private int id;
    private float price;
    private LocalDateTime date;

    public Price() {
    }

    public Price(int id, float price, LocalDateTime date) {
        this.id = id;
        this.price = price;
        this.date = date;
    }

    public Price(float price, LocalDateTime date) {
        this.price = price;
        this.date = date;
    }

    public Price(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
