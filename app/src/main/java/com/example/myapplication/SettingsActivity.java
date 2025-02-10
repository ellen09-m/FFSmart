package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private Switch darkModeToggle;
    private SeekBar textSizeSeekBar;
    private Button saveSettingsButton, resetDefaultsButton, logoutButton;
    private ImageView hamburgerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize UI elements (no redeclaration of the variables)
        darkModeToggle = findViewById(R.id.switch_dark_mode);
        textSizeSeekBar = findViewById(R.id.text_size_seekbar);
        saveSettingsButton = findViewById(R.id.btn_save_settings);
        resetDefaultsButton = findViewById(R.id.btn_reset_defaults);
        logoutButton = findViewById(R.id.btn_logout);
        hamburgerIcon = findViewById(R.id.hamburger_icon);

        hamburgerIcon.setOnClickListener(this::showPopupMenu);

        // Retrieve saved preferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        int textSize = sharedPreferences.getInt("textSize", 16);

        // Set saved preferences to UI elements
        darkModeToggle.setChecked(isDarkMode);
        textSizeSeekBar.setProgress(textSize - 10);

        // Toggle Dark Mode
        //darkModeToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putBoolean("darkMode", isChecked);
        //editor.apply();

        // Apply dark mode
        //if (isChecked) {
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //} else {
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // }
        //});

        // Handle Text Size SeekBar
        textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int adjustedTextSize = progress + 10; // Adjust for a minimum text size of 10
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("textSize", adjustedTextSize);
                editor.apply();

                // Apply the new text size to all views
                adjustTextSizeForAllViews(findViewById(android.R.id.content), adjustedTextSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        // Save Settings Button
        saveSettingsButton.setOnClickListener(v -> {
            // Save current settings
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("darkMode", darkModeToggle.isChecked());
            editor.putInt("textSize", textSizeSeekBar.getProgress() + 10);
            editor.apply();

            // Show success message
            Toast.makeText(SettingsActivity.this, "Settings saved!", Toast.LENGTH_SHORT).show();
        });

        // Reset Defaults Button
        resetDefaultsButton.setOnClickListener(v -> {
            // Reset settings to defaults
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("darkMode", false);  // Default to light mode
            editor.putInt("textSize", 16);  // Default text size
            editor.apply();

            // Apply the reset settings
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);  // Reset to light mode

            // Reset the UI elements
            darkModeToggle.setChecked(false);
            textSizeSeekBar.setProgress(6);  // Set to default text size (16)

            // Show reset message
            Toast.makeText(SettingsActivity.this, "Settings reset to default!", Toast.LENGTH_SHORT).show();

        });

        // Logout Button Listener
        logoutButton.setOnClickListener(v -> {
            SharedPreferences userPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE); // Renamed variable
            SharedPreferences.Editor editor = userPreferences.edit();
            editor.clear();  // Clear user preferences if needed
            editor.apply();

            // Navigate to the LoginActivity (replace with your actual login class)
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            Toast.makeText(SettingsActivity.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
        });

    }

    // Method to adjust text size for all views (TextViews, Buttons, etc.)
    private void adjustTextSizeForAllViews(View view, float textSize) {
        if (view == null) return;

        if (view instanceof TextView) {
            ((TextView) view).setTextSize(textSize);
        } else if (view instanceof Button) {
            ((Button) view).setTextSize(textSize);
        } else if (view instanceof Switch) {
            ((Switch) view).setTextSize(textSize);
        }

        // Recursively go through all child views in a ViewGroup
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                adjustTextSizeForAllViews(viewGroup.getChildAt(i), textSize); // Recurse through child views
            }
        }
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_dashboard) {
                startActivity(new Intent(SettingsActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.menu_settings) {
                startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
                return true;
            } else if (itemId == R.id.menu_admin) {
                startActivity(new Intent(SettingsActivity.this, AdminActivity.class));
                return true;
            } else if (itemId == R.id.menu_inventory) {
                startActivity(new Intent(SettingsActivity.this, InventoryActivity.class));
                return true;
            } else if (itemId == R.id.menu_alerts) {
                startActivity(new Intent(SettingsActivity.this, MaintenanceActivity.class));
                return true;
            } else if (itemId == R.id.menu_reorder) {
                startActivity(new Intent(SettingsActivity.this, ReorderActivity.class));
                return true;
            } else if (itemId == R.id.menu_health) {
                startActivity(new Intent(SettingsActivity.this, HealthSafetyActivity.class));
                return true;
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }
}

