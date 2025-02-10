package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ReorderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private Basket basket;
    private ImageView cartIcon;
    private ImageView hamburgerIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reorder);

        Log.d("ReorderActivity", "Activity created");

        recyclerView = findViewById(R.id.food_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the shared Basket instance
        basket = Basket.getInstance();

        cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(v -> {
            Log.d("BasketNavigation", "Navigating to BasketActivity");
            Intent intent = new Intent(ReorderActivity.this, BasketActivity.class);
            startActivity(intent);
        });

        // Initialize the hamburger menu icon
        hamburgerIcon = findViewById(R.id.hamburger_icon);
        hamburgerIcon.setOnClickListener(v -> showPopupMenu(v));


        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("Apples", 2.99));
        foodList.add(new Food("Beef", 100));
        foodList.add(new Food("Chicken", 200));
        foodList.add(new Food("Carrots", 1.99));
        foodList.add(new Food("Prawns", 50));
        foodList.add(new Food("Steak", 210));

        // Set up the adapter with the list and basket
        foodAdapter = new FoodAdapter(foodList, this, basket);
        recyclerView.setAdapter(foodAdapter);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_dashboard) {
                startActivity(new Intent(ReorderActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.menu_settings) {
                startActivity(new Intent(ReorderActivity.this, SettingsActivity.class));
                return true;
            } else if (itemId == R.id.menu_admin) {
                startActivity(new Intent(ReorderActivity.this, AdminActivity.class));
                return true;
            } else if (itemId == R.id.menu_inventory) {
                startActivity(new Intent(ReorderActivity.this, InventoryActivity.class));
                return true;
            } else if (itemId == R.id.menu_alerts) {
                startActivity(new Intent(ReorderActivity.this, MaintenanceActivity.class));
                return true;
            } else if (itemId == R.id.menu_reorder) {
                startActivity(new Intent(ReorderActivity.this, ReorderActivity.class));
                return true;
            } else if (itemId == R.id.menu_health) {
                startActivity(new Intent(ReorderActivity.this, HealthSafetyActivity.class));
                return true;
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(ReorderActivity.this, ProfileActivity.class));
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }
}
