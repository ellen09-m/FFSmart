package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.myapplication.SubmitReportActivity;
import com.example.myapplication.ContactRestaurantActivity;
import com.example.myapplication.ChatsActivity;
import com.example.myapplication.R;





public class HealthSafetyActivity extends AppCompatActivity {

    private Button btnPreviousReport, btnSubmitReport, btnContactRestaurant, btnChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_safety);

        // Initialize buttons
        btnPreviousReport = findViewById(R.id.btnPreviousReport);
        btnSubmitReport = findViewById(R.id.btnSubmitReport);
        btnContactRestaurant = findViewById(R.id.btnContactRestaurant);
        btnChats = findViewById(R.id.btnChats);

        // Set button click listeners
        btnPreviousReport.setOnClickListener(view -> {
            Intent intent = new Intent(HealthSafetyActivity.this, PreviousReportActivity.class);
            startActivity(intent);
        });

        btnSubmitReport.setOnClickListener(view -> {
            Intent intent = new Intent(HealthSafetyActivity.this, SubmitReportActivity.class);
            startActivity(intent);
        });


        btnContactRestaurant.setOnClickListener(view -> {
            Intent intent = new Intent(HealthSafetyActivity.this, ContactRestaurantActivity.class);
            startActivity(intent);
        });

        btnChats.setOnClickListener(view -> {
            Intent intent = new Intent(HealthSafetyActivity.this, ChatsActivity.class);
            startActivity(intent);
        });

        // Initialize BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set listener for navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_explore) {
                startActivity(new Intent(HealthSafetyActivity.this, ExploreActivity.class));
                return true;
            } else if (itemId == R.id.nav_saved) {
                startActivity(new Intent(HealthSafetyActivity.this, SavedActivity.class));
                return true;
            } else if (itemId == R.id.nav_updates) {
                startActivity(new Intent(HealthSafetyActivity.this, UpdatesActivity.class));
                return true;
            }
            return false;
        });
    }
}
