package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ContactRestaurantActivity extends AppCompatActivity {
    private Button btnCallRestaurant, btnEmailRestaurant, btnViewLocation;
    private static final String RESTAURANT_PHONE = "+1234567890"; // Change to actual phone number
    private static final String RESTAURANT_EMAIL = "restaurant@example.com"; // Change to actual email
    private static final String RESTAURANT_LOCATION = "geo:0,0?q=Restaurant Name, City"; // Change to actual location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_restaurant);

        // Initialize Buttons
        btnCallRestaurant = findViewById(R.id.btnCallRestaurant);
        btnEmailRestaurant = findViewById(R.id.btnEmailRestaurant);
        btnViewLocation = findViewById(R.id.btnViewLocation);

        // Call the Restaurant
        btnCallRestaurant.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + RESTAURANT_PHONE));
            startActivity(intent);
        });

        // Send Email to the Restaurant
        btnEmailRestaurant.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + RESTAURANT_EMAIL));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
            startActivity(intent);
        });

        // View Restaurant Location in Google Maps
        btnViewLocation.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(RESTAURANT_LOCATION));
            startActivity(intent);
        });
    }
}
