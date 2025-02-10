package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EditText inputId, inputName, inputLastName, inputEmail;
    private Button btnSignIn, btnRegister, btnShowRegister;
    private LinearLayout registerForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Log activity creation
        Log.d("MainActivity", "Activity created");

        // Initialize the views
        inputId = findViewById(R.id.input_id);
        inputName = findViewById(R.id.input_name);
        inputLastName = findViewById(R.id.input_last_name);
        inputEmail = findViewById(R.id.input_email);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnRegister = findViewById(R.id.btn_register);
        btnShowRegister = findViewById(R.id.btn_show_register);
        registerForm = findViewById(R.id.register_form);

        // Set the sign-in button click listener
        btnSignIn.setOnClickListener(v -> {
            // Get the user input
            String id = inputId.getText().toString().trim();
            Log.d("MainActivity", "Sign-in attempted with ID: " + id);

            // Validate the input
            if (TextUtils.isEmpty(id) || id.length() != 6) {
                Toast.makeText(MainActivity.this, "Enter a valid ID", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Invalid ID input");
                return;
            }

            // Check if the ID starts with 'D' (for delivery access)
            if (isDeliveryId(id)) {
                // Log the action
                Log.i("MainActivity", "Delivery ID detected. Navigating to DeliveryActivity");
                // Navigate to DeliveryActivity
                Intent intent = new Intent(MainActivity.this, DeliveryActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish this activity so the user can't go back
            } else {
                // Existing check for admin or staff IDs
                if (isAdminId(id)) {
                    Log.i("MainActivity", "Admin ID detected. Navigating to DashboardActivity");
                    // Navigate to AdminActivity with admin access
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.putExtra("isAdmin", true);
                    startActivity(intent);
                } else if (isStaffId(id)) {
                    Log.i("MainActivity", "Staff ID detected. Navigating to DashboardActivity");
                    // Navigate to AdminActivity with staff access
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.putExtra("isAdmin", false);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid ID", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Invalid ID: " + id);
                }
            }
        });

        // Set the register button click listener
        btnRegister.setOnClickListener(v -> {
            String firstName = inputName.getText().toString().trim(); // First Name
            String lastName = inputLastName.getText().toString().trim(); // Last Name
            String userEmail = inputEmail.getText().toString().trim(); // Email

            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(userEmail)) {
                Toast.makeText(MainActivity.this, "Enter your first name, last name, and email", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Registration failed: Missing required fields");
                return;
            }

            // Generate unique ID
            String uniqueId = "A" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
            Log.d("MainActivity", "Generated unique ID: " + uniqueId);

            // Save the user's data in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("firstName", firstName);  // Save First Name
            editor.putString("lastName", lastName);    // Save Last Name
            editor.putString("userEmail", userEmail);  // Save Email
            editor.putString("userId", uniqueId);      // Save Unique ID
            editor.apply(); // Save the data
            Log.d("MainActivity", "User data saved in SharedPreferences");

            // Send email (mock)
            sendEmail(userEmail, firstName, uniqueId);

            // Inform the user
            Toast.makeText(MainActivity.this, "Registration Successful! Check your email for ID.", Toast.LENGTH_SHORT).show();
            Log.i("MainActivity", "Registration successful for " + firstName + " " + lastName);

            // Clear the input fields
            inputName.setText("");
            inputLastName.setText(""); // Clear Last Name field
            inputEmail.setText("");
        });

        // Show register form on button click
        btnShowRegister.setOnClickListener(v -> {
            if (registerForm.getVisibility() == View.GONE) {
                registerForm.setVisibility(View.VISIBLE);
                Log.d("MainActivity", "Register form shown");
            } else {
                registerForm.setVisibility(View.GONE);
                Log.d("MainActivity", "Register form hidden");
            }
        });
    }

    private boolean isAdminId(String id) {
        // Replace with your actual logic to check admin ID
        return id.startsWith("A");
    }

    private boolean isStaffId(String id) {
        // Replace with your actual logic to check staff ID
        return id.startsWith("S");
    }

    private boolean isDeliveryId(String id) {
        // Logic to check if ID starts with 'D' for Delivery persons
        return id.startsWith("D");
    }

    private void sendEmail(String email, String name, String uniqueId) {
        String subject = "Welcome to FFSmart";
        String message = "Hello " + name + ",\n\n" +
                "You have been registered to FFSmart. Your unique ID is: " + uniqueId + ".\n" +
                "Please use this ID to log in.\n\n" +
                "Best regards,\nFFSmart Team";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            Log.d("MainActivity", "Email sent to " + email);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "No email clients installed");
        }
    }
}
