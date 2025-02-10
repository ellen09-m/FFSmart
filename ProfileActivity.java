package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView firstNameTextView, lastNameTextView, emailTextView, userIdTextView;
    private Button saveChangesButton;
    private ImageView profileImageView, settingsIcon, hamburgerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize UI elements
        firstNameTextView = findViewById(R.id.first_name);
        lastNameTextView = findViewById(R.id.last_name);
        emailTextView = findViewById(R.id.user_email);
        userIdTextView = findViewById(R.id.user_id);
        saveChangesButton = findViewById(R.id.btn_save_changes);
        profileImageView = findViewById(R.id.profile_image);
        settingsIcon = findViewById(R.id.settings_icon);
        hamburgerIcon = findViewById(R.id.hamburger_icon);

        // Disable the user ID TextView
        userIdTextView.setEnabled(false);

        hamburgerIcon.setOnClickListener(v -> showPopupMenu(v));

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userFirstName = sharedPreferences.getString("firstName", "Default First Name");
        String userLastName = sharedPreferences.getString("lastName", "Default Last Name");
        String userEmail = sharedPreferences.getString("userEmail", "Default Email");
        String userId = sharedPreferences.getString("userId", "Default ID");

        // Set the data to TextView fields
        firstNameTextView.setText(userFirstName);
        lastNameTextView.setText(userLastName);
        emailTextView.setText(userEmail);
        userIdTextView.setText(userId);

        // Save changes when the button is clicked
        saveChangesButton.setOnClickListener(v -> {
            // Save updated data back to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("firstName", firstNameTextView.getText().toString());
            editor.putString("lastName", lastNameTextView.getText().toString());
            editor.putString("userEmail", emailTextView.getText().toString());
            editor.apply();
        });

        // Make the settings icon clickable
        settingsIcon.setOnClickListener(v -> onSettingsClick());
    }

    // Handle settings click
    public void onSettingsClick() {
        Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_dashboard) {
                startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.menu_settings) {
                startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
                return true;
            } else if (itemId == R.id.menu_admin) {
                startActivity(new Intent(ProfileActivity.this, AdminActivity.class));
                return true;
            } else if (itemId == R.id.menu_inventory) {
                startActivity(new Intent(ProfileActivity.this, InventoryActivity.class));
                return true;
            } else if (itemId == R.id.menu_alerts) {
                startActivity(new Intent(ProfileActivity.this, MaintenanceActivity.class));
                return true;
            } else if (itemId == R.id.menu_reorder) {
                startActivity(new Intent(ProfileActivity.this, ReorderActivity.class));
                return true;
            } else if (itemId == R.id.menu_health) {
                startActivity(new Intent(ProfileActivity.this, HealthSafetyActivity.class));
                return true;
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
            return true;
            } else {
                return false;
            }
        });
        popupMenu.show();


    }
}