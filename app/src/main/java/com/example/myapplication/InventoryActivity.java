package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InventoryActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private ImageView searchIcon;
    private SearchView searchView;
    private LinearLayout searchContainer;
    private ArrayList<Item> inventoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout);
        Button addRowButton = findViewById(R.id.addRowButton);
        Button createReportButton = findViewById(R.id.createReportButton);
        searchIcon = findViewById(R.id.searchIcon);
        searchContainer = findViewById(R.id.searchContainer);
        searchView = findViewById(R.id.searchView);
        ImageView hamburgerIcon = findViewById(R.id.hamburger_icon);

        addRowButton.setOnClickListener(v -> showAddRowDialog());
        searchIcon.setOnClickListener(v -> showSearchView());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterTable(query);
                hideSearchView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTable(newText);
                return false;
            }
        });

        createReportButton.setOnClickListener(v -> {
            Toast.makeText(InventoryActivity.this, "Create Report Button Clicked", Toast.LENGTH_SHORT).show();
            showReportDialog();
        });

        hamburgerIcon.setOnClickListener(v -> showPopupMenu(v));

        // Load the saved table data when the activity starts
        loadTableData();
    }

    // Show the SearchView when the search icon is clicked
    private void showSearchView() {
        searchContainer.setVisibility(View.VISIBLE);
    }

    // Hide the SearchView container after the search is completed
    private void hideSearchView() {
        searchContainer.setVisibility(View.GONE);
    }

    // Filter the table rows based on the search query
    private void filterTable(String query) {
        boolean found = false;

        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            TextView itemTextView = (TextView) tableRow.getChildAt(0);
            String itemName = itemTextView.getText().toString().toLowerCase();

            if (itemName.contains(query.toLowerCase())) {
                tableRow.setVisibility(View.VISIBLE);
                found = true;
            } else {
                tableRow.setVisibility(View.GONE);
            }
        }
        if (!found && !query.isEmpty()) {
            Toast.makeText(InventoryActivity.this, "No items found", Toast.LENGTH_SHORT).show();
        }
        // Hide the search view if no results and query is empty
        if (!found && !query.isEmpty()) {
            searchContainer.setVisibility(View.GONE);
        }
    }

    private void showAddRowDialog() {
        Dialog dialog = new Dialog(InventoryActivity.this);
        dialog.setContentView(R.layout.dialog_add_row);
        dialog.setCancelable(true);

        EditText itemNameEditText = dialog.findViewById(R.id.itemNameEditText);
        EditText quantityEditText = dialog.findViewById(R.id.quantityEditText);
        EditText priceEditText = dialog.findViewById(R.id.expiryDateEditText);
        Button saveButton = dialog.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            String itemName = itemNameEditText.getText().toString();
            String quantity = quantityEditText.getText().toString();
            String price = priceEditText.getText().toString();

            addRowToTable(itemName, quantity, price);
            dialog.dismiss();
            // Save data after adding new row
            saveTableData();
        });

        dialog.show();
    }

    private void addRowToTable(String itemName, String quantity, String price) {
        TableRow tableRow = new TableRow(this);

        TextView itemTextView = new TextView(this);
        itemTextView.setText(itemName);

        TextView quantityTextView = new TextView(this);
        quantityTextView.setText(quantity);

        TextView expiryDateTextView = new TextView(this);
        expiryDateTextView.setText(price);

        tableRow.addView(itemTextView);
        tableRow.addView(quantityTextView);
        tableRow.addView(expiryDateTextView);

        tableLayout.addView(tableRow);
    }

    // Save the table data to SharedPreferences
    private void saveTableData() {
        SharedPreferences sharedPreferences = getSharedPreferences("InventoryData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Loop through each row in the table and save the data
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            TextView itemNameTextView = (TextView) tableRow.getChildAt(0);
            TextView quantityTextView = (TextView) tableRow.getChildAt(1);
            TextView expiryDateTextView = (TextView) tableRow.getChildAt(2);

            String itemName = itemNameTextView.getText().toString();
            String quantity = quantityTextView.getText().toString();
            String expiryDate = expiryDateTextView.getText().toString();

            editor.putString("item_" + i + "_name", itemName);
            editor.putString("item_" + i + "_quantity", quantity);
            editor.putString("item_" + i + "_expiryDate", expiryDate);
        }

        // Commit the changes
        editor.apply();
    }

    // Load the table data from SharedPreferences
    private void loadTableData() {
        SharedPreferences sharedPreferences = getSharedPreferences("InventoryData", MODE_PRIVATE);

        // Loop through the stored data and populate the table
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);

            String itemName = sharedPreferences.getString("item_" + i + "_name", "");
            String quantity = sharedPreferences.getString("item_" + i + "_quantity", "");
            String expiryDate = sharedPreferences.getString("item_" + i + "_expiryDate", "");

            TextView itemNameTextView = (TextView) tableRow.getChildAt(0);
            TextView quantityTextView = (TextView) tableRow.getChildAt(1);
            TextView expiryDateTextView = (TextView) tableRow.getChildAt(2);

            itemNameTextView.setText(itemName);
            quantityTextView.setText(quantity);
            expiryDateTextView.setText(expiryDate);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTableData(); // Load data when activity is resumed
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveTableData(); // Save data when activity is paused
    }

    private void showReportDialog() {
        String[] reportOptions = {"Weekly Report", "Monthly Report"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Report Type")
                .setItems(reportOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            generateWeeklyReport();
                        } else {
                            generateMonthlyReport();
                        }
                    }
                })
                .show();
    }

    private void generateWeeklyReport() {
        StringBuilder report = new StringBuilder("Weekly Report\n\n");
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // One week in milliseconds
        Date currentDate = new Date();

        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            TextView itemTextView = (TextView) tableRow.getChildAt(0);
            TextView quantityTextView = (TextView) tableRow.getChildAt(1);
            TextView expiryDateTextView = (TextView) tableRow.getChildAt(2);

            String itemName = itemTextView.getText().toString();
            String quantity = quantityTextView.getText().toString();
            String expiryDateStr = expiryDateTextView.getText().toString();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date expiryDate = sdf.parse(expiryDateStr);

                if (expiryDate != null && expiryDate.getTime() - currentDate.getTime() <= oneWeekInMillis) {
                    report.append("Item: ").append(itemName)
                            .append(", Quantity: ").append(quantity)
                            .append(", Expiry Date: ").append(expiryDateStr).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(InventoryActivity.this, "Weekly Report Generated", Toast.LENGTH_SHORT).show();

        showReportDialog(report.toString());
    }

    private void generateMonthlyReport() {
        StringBuilder report = new StringBuilder("Monthly Report\n\n");
        long oneMonthInMillis = 30 * 24 * 60 * 60 * 1000; // One month in milliseconds
        Date currentDate = new Date();

        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            TextView itemTextView = (TextView) tableRow.getChildAt(0);
            TextView quantityTextView = (TextView) tableRow.getChildAt(1);
            TextView expiryDateTextView = (TextView) tableRow.getChildAt(2);

            String itemName = itemTextView.getText().toString();
            String quantity = quantityTextView.getText().toString();
            String expiryDateStr = expiryDateTextView.getText().toString();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date expiryDate = sdf.parse(expiryDateStr);

                if (expiryDate != null && expiryDate.getTime() - currentDate.getTime() <= oneMonthInMillis) {
                    report.append("Item: ").append(itemName)
                            .append(", Quantity: ").append(quantity)
                            .append(", Expiry Date: ").append(expiryDateStr).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        showReportDialog(report.toString());
    }

    private void showReportDialog(String report) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Generated Report")
                .setMessage(report)
                .setPositiveButton("OK", null)
                .show();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_dashboard) {
                startActivity(new Intent(InventoryActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.menu_settings) {
                startActivity(new Intent(InventoryActivity.this, SettingsActivity.class));
                return true;
            } else if (itemId == R.id.menu_admin) {
                startActivity(new Intent(InventoryActivity.this, AdminActivity.class));
                return true;
            } else if (itemId == R.id.menu_inventory) {
                startActivity(new Intent(InventoryActivity.this, InventoryActivity.class));
                return true;
            } else if (itemId == R.id.menu_alerts) {
                startActivity(new Intent(InventoryActivity.this, MaintenanceActivity.class));
                return true;
            } else if (itemId == R.id.menu_reorder) {
                startActivity(new Intent(InventoryActivity.this, ReorderActivity.class));
                return true;
            } else if (itemId == R.id.menu_health) {
                startActivity(new Intent(InventoryActivity.this, HealthSafetyActivity.class));
                return true;
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(InventoryActivity.this,InventoryActivity.class));
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }
}

