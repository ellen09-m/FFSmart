package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PreviousReportActivity extends AppCompatActivity {
    private ListView listViewReports;
    private SharedPreferences sharedPreferences;
    private List<String> reportList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_report);

        listViewReports = findViewById(R.id.listViewReports);
        sharedPreferences = getSharedPreferences("ReportsPrefs", Context.MODE_PRIVATE);

        reportList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reportList);
        listViewReports.setAdapter(adapter);

        loadReports();
    }

    private void loadReports() {
        Set<String> reportSet = sharedPreferences.getStringSet("reports", null);
        if (reportSet != null) {
            reportList.clear();
            reportList.addAll(reportSet);
            adapter.notifyDataSetChanged();
        }
    }
}
