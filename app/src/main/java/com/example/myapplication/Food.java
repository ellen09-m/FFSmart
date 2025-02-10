package com.example.myapplication;

public class Food {
    private String name;

    private double price;  // New price attribute

    public Food(String name, double price) {
        this.name = name;

        this.price = price;  // Initialize price
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
