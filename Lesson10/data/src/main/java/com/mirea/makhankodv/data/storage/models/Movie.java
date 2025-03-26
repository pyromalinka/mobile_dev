package com.mirea.makhankodv.data.storage.models;

public class Movie {
    private int id;
    private String name;
    private String date;

    public Movie(int id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
} 