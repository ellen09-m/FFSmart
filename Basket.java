package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    private static Basket instance;
    private List<BasketItem> basketItems;

    // Private constructor to implement Singleton pattern
    private Basket() {
        basketItems = new ArrayList<>();
    }

    // Get the single instance of Basket
    public static Basket getInstance() {
        if (instance == null) {
            instance = new Basket();
        }
        return instance;
    }

    // Method to get the basket items
    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    // Method to add an item to the basket
    public void addItem(BasketItem item) {
        basketItems.add(item);
    }
    public void removeItem(BasketItem item) {
        basketItems.remove(item);
    }

    // Method to calculate and return the total price of the basket items
    public double getTotalPrice() {
        double total = 0.0;

        // Loop through the basket items and sum up their prices
        for (BasketItem item : basketItems) {
            total += item.getPrice();
        }

        return total;
    }

}
