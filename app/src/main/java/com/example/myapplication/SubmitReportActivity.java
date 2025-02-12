package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;

public class SubmitReportActivity extends AppCompatActivity {
    private EditText editReportTitle, editReportDescription;
    private Spinner spinnerReportType;
    private Button btnSubmitReport;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);

        // Initialize UI Elements
        editReportTitle = findViewById(R.id.editReportTitle);
        editReportDescription = findViewById(R.id.editReportDescription);
        spinnerReportType = findViewById(R.id.spinnerReportType);
        btnSubmitReport = findViewById(R.id.btnSubmitReport);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("ReportsPrefs", Context.MODE_PRIVATE);

        // Populate Spinner with Report Types
        String[] reportTypes = {"Select Report Type", "Food Safety", "Equipment Issue", "Health Violation", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, reportTypes);
        spinnerReportType.setAdapter(adapter);

        // Handle Submit Button Click
        btnSubmitReport.setOnClickListener(view -> submitReport());
    }

    private void submitReport() {
        String title = editReportTitle.getText().toString().trim();
        String description = editReportDescription.getText().toString().trim();
        String reportType = spinnerReportType.getSelectedItem().toString();

        // Validate input fields
        if (title.isEmpty() || description.isEmpty() || reportType.equals("Select Report Type")) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve previous reports
        Set<String> reportSet = sharedPreferences.getStringSet("reports", new HashSet<>());
        reportSet.add(reportType + " - " + title);

        // Save updated reports list
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("reports", reportSet);
        editor.apply();

        Toast.makeText(this, "Report Submitted Successfully!", Toast.LENGTH_SHORT).show();

        // Clear Fields After Submission
        editReportTitle.setText("");
        editReportDescription.setText("");
        spinnerReportType.setSelection(0);
    }
}
