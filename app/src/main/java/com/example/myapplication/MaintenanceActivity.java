package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import android.content.Intent;
import android.app.Dialog;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceActivity extends AppCompatActivity {

    private RecyclerView faqRecyclerView;
    private FAQAdapter faqAdapter;
    private List<FAQ> faqList;
    private Button contactSupportButton;
    private Button addMaintenanceIssueButton;
    private ImageView hamburgerIcon; // For the menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        faqRecyclerView = findViewById(R.id.faqRecyclerView);
        faqRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactSupportButton = findViewById(R.id.contactSupportButton);
        addMaintenanceIssueButton = findViewById(R.id.addMaintenanceIssueButton);
        hamburgerIcon = findViewById(R.id.hamburger_icon); // Link to XML


        faqList = new ArrayList<>();
        faqList.add(new FAQ("How can I contact support?", "You can contact support by clicking the 'Contact Support' button above."));
        faqList.add(new FAQ("What should I do if my fridge is not cooling?", "Ensure the power is on, and check the settings. If the issue persists, contact support."));
        faqList.add(new FAQ("How do I reset my fridge?", "To reset, press and hold the reset button located at the back of the unit."));
        faqList.add(new FAQ("How do I change the fridge filter?", "Locate the filter compartment, remove the old filter, and replace it with a new one. Follow the manufacturer’s instructions for model-specific steps."));
        faqList.add(new FAQ("How often should I defrost my fridge?", "It’s recommended to defrost your fridge every 6-12 months, or when ice buildup exceeds 1/4 inch thick."));
        faqList.add(new FAQ("How do I calibrate the fridge temperature?", "Use a thermometer inside the fridge and adjust the thermostat accordingly. Wait 24 hours after adjusting before rechecking the temperature."));

        faqAdapter = new FAQAdapter(faqList);
        faqRecyclerView.setAdapter(faqAdapter);

        addMaintenanceIssueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaintenanceActivity.this, LogMaintenanceActivity.class);
                startActivity(intent);
            }
        });

        contactSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSupportDialog();
            }
        });

        // Set click listener for hamburger menu
        hamburgerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_dashboard) {
                startActivity(new Intent(MaintenanceActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.menu_settings) {
                startActivity(new Intent(MaintenanceActivity.this, SettingsActivity.class));
                return true;
            } else if (itemId == R.id.menu_admin) {
                startActivity(new Intent(MaintenanceActivity.this, AdminActivity.class));
                return true;
            } else if (itemId == R.id.menu_inventory) {
                startActivity(new Intent(MaintenanceActivity.this, InventoryActivity.class));
                return true;
            } else if (itemId == R.id.menu_alerts) {
                startActivity(new Intent(MaintenanceActivity.this, MaintenanceActivity.class));
                return true;
            } else if (itemId == R.id.menu_reorder) {
                startActivity(new Intent(MaintenanceActivity.this, ReorderActivity.class));
                return true;
            } else if (itemId == R.id.menu_health) {
                startActivity(new Intent(MaintenanceActivity.this, HealthSafetyActivity.class));
                return true;
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(MaintenanceActivity.this, ProfileActivity.class));
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    private void showSupportDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.support_dialog);

        TextView phoneNumber = dialog.findViewById(R.id.phoneNumberText);
        EditText emailName = dialog.findViewById(R.id.emailName);
        EditText emailSubject = dialog.findViewById(R.id.emailSubject);
        EditText emailMessage = dialog.findViewById(R.id.emailMessage);
        Button sendEmailButton = dialog.findViewById(R.id.sendEmailButton);

        phoneNumber.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+18001234567"));
            startActivity(callIntent);
        });

        sendEmailButton.setOnClickListener(v -> {
            String name = emailName.getText().toString().trim();
            String subject = emailSubject.getText().toString().trim();
            String message = emailMessage.getText().toString().trim();

            if (!subject.isEmpty() && !message.isEmpty()) {
                sendEmail(name, subject, message);
                dialog.dismiss();
            } else {
                emailSubject.setError("Subject required");
                emailMessage.setError("Message required");
            }
        });

        dialog.show();
    }

    private void sendEmail(String name, String subject, String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:support@ffsmartfridges.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "From: " + name + "\n\n" + message);

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }
}
