package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView; // Import the correct SearchView

import java.util.ArrayList;
import java.util.List;

public class LogMaintenanceActivity extends AppCompatActivity {

    private EditText issueEditText, dateOccurredEditText, dateFixedEditText, fixDescriptionEditText;
    private Button submitButton;

    private TableLayout maintenanceTable;
    private SearchView searchView; // Declare SearchView
    private List<MaintenanceIssue> maintenanceIssueList = new ArrayList<>(); // List to store issues for filtering

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_maintenance);

        // Initialize UI components
        issueEditText = findViewById(R.id.issueEditText);
        dateOccurredEditText = findViewById(R.id.dateOccurredEditText);
        dateFixedEditText = findViewById(R.id.dateFixedEditText);
        fixDescriptionEditText = findViewById(R.id.fixDescriptionEditText);
        submitButton = findViewById(R.id.submitButton);
        maintenanceTable = findViewById(R.id.maintenanceTable);  // Get the TableLayout
        searchView = findViewById(R.id.searchView); // Initialize SearchView

        // Handle Search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTable(newText); // Filter table rows based on search input
                return true;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from EditText fields
                String issue = issueEditText.getText().toString().trim();
                String dateOccurred = dateOccurredEditText.getText().toString().trim();
                String dateFixed = dateFixedEditText.getText().toString().trim();
                String fixDescription = fixDescriptionEditText.getText().toString().trim();

                // Validate inputs
                if (issue.isEmpty() || dateOccurred.isEmpty() || dateFixed.isEmpty() || fixDescription.isEmpty()) {
                    Toast.makeText(LogMaintenanceActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a new MaintenanceIssue object
                    MaintenanceIssue maintenanceIssue = new MaintenanceIssue(issue, dateOccurred, dateFixed, fixDescription);

                    // Add to list of issues
                    maintenanceIssueList.add(maintenanceIssue);

                    // Add a new row to the table
                    addRowToTable(maintenanceIssue);

                    dateOccurredEditText.setText("");
                    dateFixedEditText.setText("");
                    fixDescriptionEditText.setText("");

                    // Show success message
                    Toast.makeText(LogMaintenanceActivity.this, "Maintenance Issue Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to filter the table based on search query
    private void filterTable(String query) {
        // Clear the table first
        maintenanceTable.removeAllViewsInLayout();

        // Add the table header again
        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("Issue"));
        headerRow.addView(createTextView("Date Occurred"));
        headerRow.addView(createTextView("Date Fixed"));
        headerRow.addView(createTextView("Fix Description"));
        maintenanceTable.addView(headerRow);

        // Loop through the list of maintenance issues and add matching rows to the table
        for (MaintenanceIssue issue : maintenanceIssueList) {
            if (issue.getIssue().toLowerCase().contains(query.toLowerCase())) {
                addRowToTable(issue);
            }
        }
    }

    // Helper method to add a row to the table
    private void addRowToTable(MaintenanceIssue maintenanceIssue) {
        TableRow row = new TableRow(this);
        row.addView(createTextView(maintenanceIssue.getIssue()));
        row.addView(createTextView(maintenanceIssue.getDateOccurred()));
        row.addView(createTextView(maintenanceIssue.getDateFixed()));
        row.addView(createTextView(maintenanceIssue.getFixDescription()));
        maintenanceTable.addView(row);
    }

    // Helper method to create a TextView for the table row
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(16, 16, 16, 16);  // Optional padding for better readability
        return textView;
    }

    // MaintenanceIssue class to hold issue data
    private static class MaintenanceIssue {
        private String issue;
        private String dateOccurred;
        private String dateFixed;
        private String fixDescription;

        public MaintenanceIssue(String issue, String dateOccurred, String dateFixed, String fixDescription) {
            this.issue = issue;
            this.dateOccurred = dateOccurred;
            this.dateFixed = dateFixed;
            this.fixDescription = fixDescription;
        }

        public String getIssue() {
            return issue;
        }

        public String getDateOccurred() {
            return dateOccurred;
        }

        public String getDateFixed() {
            return dateFixed;
        }

        public String getFixDescription() {
            return fixDescription;
        }
    }
}
