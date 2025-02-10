package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView upcomingEventsText;
    private SharedPreferences preferences;
    private ImageView hamburgerIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize the CardViews
        CardView reorderCard = findViewById(R.id.reorderCard);
        CardView inventoryCard = findViewById(R.id.inventoryCard);
        calendarView = findViewById(R.id.calendarView);
        hamburgerIcon = findViewById(R.id.hamburger_icon);
        upcomingEventsText = findViewById(R.id.upcomingEventsText);

        // Display today's events when the dashboard loads
        displayUpcomingEvents(getCurrentDate());

        // Set onClickListener for reorderCard
        reorderCard.setOnClickListener(v -> {
            // Navigate to the Reorder page
            startActivity(new Intent(DashboardActivity.this, ReorderActivity.class));
        });

        // Set onClickListener for inventoryCard
        inventoryCard.setOnClickListener(v -> {
            // Navigate to the Inventory page
            startActivity(new Intent(DashboardActivity.this, InventoryActivity.class));
        });

        // Handle calendar date selection
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;

            // Pass the selected date to EventActivity
            Intent intent = new Intent(DashboardActivity.this, EventActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        });

        // Set click listener for hamburger menu
        hamburgerIcon.setOnClickListener(v -> showPopupMenu(v));
    }

    // Method to get today's date in d/M/yyyy format
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        return dateFormat.format(new Date());
    }

    // Method to retrieve events for a specific date from SharedPreferences
    private ArrayList<String> getEventsForDate(String date) {
        ArrayList<String> events = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("events", MODE_PRIVATE);

        // Look for events stored with the selected date
        for (Map.Entry<String, ?> entry : preferences.getAll().entrySet()) {
            if (entry.getKey().contains(date)) {
                events.add((String) entry.getValue()); // Add matching events
            }
        }
        return events;
    }

    // Method to display upcoming events in the dashboard
    private void displayUpcomingEvents(String date) {
        ArrayList<String> events = getEventsForDate(date);

        if (events.isEmpty()) {
            upcomingEventsText.setText("No events for today.");
        } else {
            StringBuilder eventsText = new StringBuilder();
            for (String event : events) {
                eventsText.append(event).append("\n");
            }
            upcomingEventsText.setText(eventsText.toString());
        }
    }

    private void showPopupMenu(View view) {
        try {
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                try {
                    int itemId = item.getItemId();
                    Intent intent = null;

                    if (itemId == R.id.menu_dashboard) {
                        intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                    } else if (itemId == R.id.menu_settings) {
                        intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                    } else if (itemId == R.id.menu_admin) {
                        intent = new Intent(DashboardActivity.this, AdminActivity.class);
                    } else if (itemId == R.id.menu_inventory) {
                        intent = new Intent(DashboardActivity.this, InventoryActivity.class);
                    } else if (itemId == R.id.menu_alerts) {
                        intent = new Intent(DashboardActivity.this, MaintenanceActivity.class);
                    } else if (itemId == R.id.menu_reorder) {
                        intent = new Intent(DashboardActivity.this, ReorderActivity.class);
                    } else if (itemId == R.id.menu_health) {
                        intent = new Intent(DashboardActivity.this, HealthSafetyActivity.class);
                    } else if (itemId == R.id.menu_profile) {
                        intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                    } else {
                        return false;
                    }

                    if (intent != null) {
                        startActivity(intent);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    showErrorToast("Error handling menu selection.");
                    return false;
                }
            });

            popupMenu.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorToast("Error displaying menu.");
        }
    }
    private void showErrorToast(String message) {
        // Display a toast message on the UI thread
        runOnUiThread(() -> Toast.makeText(DashboardActivity.this, message, Toast.LENGTH_SHORT).show());
    }
}
