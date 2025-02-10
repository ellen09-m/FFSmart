package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import java.util.ArrayList;
import android.widget.EditText;
import java.util.Map;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {

    private String selectedDate;
    private ListView eventListView;
    private EditText eventInput;
    private Button addEventButton;
    private ArrayList<String> eventsList;
    private EventAdapter eventAdapter;
    private TextView selectedDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Get the selected date from the intent
        selectedDate = getIntent().getStringExtra("selectedDate");

        // Initialize UI components
        eventListView = findViewById(R.id.eventListView);
        eventInput = findViewById(R.id.eventInput);
        addEventButton = findViewById(R.id.addEventButton);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);

        if (selectedDate != null) {
            selectedDateTextView.setText("Selected Date: " + selectedDate);
        }

        // Initialize the events list
        eventsList = new ArrayList<>();

        // Set up the event adapter for the ListView with delete functionality
        eventAdapter = new EventAdapter(this, eventsList, position -> {
            String event = eventsList.get(position);
            eventsList.remove(position);  // Remove the selected event from the list
            eventAdapter.notifyDataSetChanged();

            // Show success message
            Toast.makeText(EventActivity.this, event + " deleted", Toast.LENGTH_SHORT).show();

            // Delete the event from SharedPreferences
            deleteEventFromDatabase(event);
        });

        // Set the adapter to the ListView
        eventListView.setAdapter(eventAdapter);

        // Load events for the selected date
        loadEventsForSelectedDate();

        // Handle adding events
        addEventButton.setOnClickListener(v -> {
            String newEvent = eventInput.getText().toString();
            if (!newEvent.isEmpty()) {
                // Add event to the list and refresh the ListView
                eventsList.add(newEvent);
                eventAdapter.notifyDataSetChanged();
                eventInput.setText(""); // Clear input field

                // Show success message
                Toast.makeText(EventActivity.this, "Event added", Toast.LENGTH_SHORT).show();

                // Save the event to SharedPreferences
                saveEventToDatabase(newEvent);
            } else {
                Toast.makeText(EventActivity.this, "Please enter an event", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle back button click
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Go back to the DashboardActivity
            Intent intent = new Intent(EventActivity.this, DashboardActivity.class);
            startActivity(intent);
        });
    }

    // Method to save event to SharedPreferences
    private void saveEventToDatabase(String event) {
        SharedPreferences preferences = getSharedPreferences("events", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("event_" + selectedDate + "_" + event, event);  // Store the event with a unique key based on the selected date
        editor.apply();
    }

    // Method to delete event from SharedPreferences
    private void deleteEventFromDatabase(String event) {
        SharedPreferences preferences = getSharedPreferences("events", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("event_" + selectedDate + "_" + event);  // Remove the event using the unique key
        editor.apply();
    }

    // Load events for the selected date
    private void loadEventsForSelectedDate() {
        SharedPreferences preferences = getSharedPreferences("events", MODE_PRIVATE);
        // Look for events by the selected date
        for (Map.Entry<String, ?> entry : preferences.getAll().entrySet()) {
            String key = entry.getKey();
            if (key.contains(selectedDate)) {
                // Add the event to the list if the key contains the selected date
                eventsList.add((String) entry.getValue());
            }
        }
        eventAdapter.notifyDataSetChanged(); // Update the list view
    }
}
