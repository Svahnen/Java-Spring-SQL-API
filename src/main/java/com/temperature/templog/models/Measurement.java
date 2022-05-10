package com.temperature.templog.models;

import java.time.LocalDateTime;

public class Measurement {
    private int id;
    private float value;
    private LocalDateTime date;
    private String type;
    private String section;

    public Measurement() {
    }

    public Measurement(int id, float value, LocalDateTime date, String type, String section) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.type = type;
        this.section = section;
    }

    public Measurement(float value, LocalDateTime date, String type, String section) {
        this.value = value;
        this.date = date;
        this.type = type;
        this.section = section;
    }

    public Measurement(float value, String type, String section) {
        this.value = value;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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