package com.temperature.templog.models;

import java.time.LocalDate;

public class Measurement {
    private int id;
    private float value;
    private LocalDate date;
    private String type;
    private String section;

    public Measurement() {
    }

    public Measurement(int id, float value, LocalDate date, String type, String section) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.type = type;
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}