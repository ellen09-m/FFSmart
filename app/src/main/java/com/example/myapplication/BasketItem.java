package com.example.myapplication;

public class BasketItem {
    private Food food;
    private String quantity;
    private double price;

    public BasketItem(Food food, String quantity) {
        this.food = food;
        this.quantity = quantity;
        this.price = calculatePrice(food,quantity);
    }
    private double calculatePrice(Food food, String quantity) {
        double basePrice = food.getPrice();
        double updatedPrice = basePrice;

        switch (quantity) {
            case "200g":
                updatedPrice = basePrice * 2;
                break;
            case "300g":
                updatedPrice = basePrice * 3;
                break;
            case "500g":
                updatedPrice = basePrice * 5;
                break;
            case "1kg":
                updatedPrice = basePrice * 10;
                break;
            case "100g":
            default:
                updatedPrice = basePrice;
                break;
        }

        return updatedPrice;
    }

    public Food getFood() {
        return food;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
        this.price = calculatePrice(food, quantity);
    }
    public double getPrice(){
        return price;
    }
}
