package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AdapterView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList;
    private Context context;
    private Basket basket;

    public FoodAdapter(List<Food> foodList, Context context, Basket basket) {
        this.foodList = foodList;
        this.context = context;
        this.basket = basket;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.foodName.setText(food.getName());

        // Set initial price (assuming the price is for 100g initially)
        updatePrice(holder, food, "100g");

        // Populate Spinner with quantities
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getQuantities());
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.quantitySpinner.setAdapter(quantityAdapter);

        // Set default selection to 100g
        holder.quantitySpinner.setSelection(0);

        // Set listener for "Add to Basket" button
        holder.addButton.setOnClickListener(v -> {
            String selectedQuantity = holder.quantitySpinner.getSelectedItem().toString();

            // Create an instance of BasketItem with selected Food and Quantity
            BasketItem basketItem = new BasketItem(food, selectedQuantity);

            // Add BasketItem to the basket
            basket.addItem(basketItem);

            // Optionally, you can display a toast message
            Toast.makeText(context, food.getName() + " added to basket with " + selectedQuantity, Toast.LENGTH_SHORT).show();
        });

        // Add a listener to the Spinner to update the price when quantity changes
        holder.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedQuantity = parentView.getItemAtPosition(position).toString();
                updatePrice(holder, food, selectedQuantity); // Update price when quantity changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    // Helper method to provide the quantities for the spinner
    private List<String> getQuantities() {
        List<String> quantities = new ArrayList<>();
        quantities.add("100g");
        quantities.add("200g");
        quantities.add("300g");
        quantities.add("500g");
        quantities.add("1kg");
        return quantities;
    }

    // Helper method to calculate and update the price
    private void updatePrice(FoodViewHolder holder, Food food, String quantity) {
        double basePrice = food.getPrice(); // Assuming price is for 100g
        double updatedPrice = basePrice;

        // Adjust the price based on the selected quantity
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

        // Update the price in the UI
        holder.foodPrice.setText("$" + String.format("%.2f", updatedPrice));
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView foodName;
        TextView foodPrice;
        Spinner quantitySpinner;
        Button addButton;

        public FoodViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice); // Initialize the price TextView
            quantitySpinner = itemView.findViewById(R.id.quantityspinner);
            addButton = itemView.findViewById(R.id.addButton);
        }
    }
}


