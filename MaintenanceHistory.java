package com.example.myapplication;

public class MaintenanceHistory {
    private String maintenanceType;
    private String date;
    private String description;
    private String issue;
    private String dateOccurred;
    private String dateFixed;
    private String fixDescription;

    // Constructor with all fields
    public MaintenanceHistory(String maintenanceType, String date, String description, String issue, String dateOccurred, String dateFixed, String fixDescription) {
        this.maintenanceType = maintenanceType;
        this.date = date;
        this.description = description;
        this.issue = issue;
        this.dateOccurred = dateOccurred;
        this.dateFixed = dateFixed;
        this.fixDescription = fixDescription;
    }

    // Getters
    public String getMaintenanceType() {
        return maintenanceType;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
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


    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setDateOccurred(String dateOccurred) {
        this.dateOccurred = dateOccurred;
    }

    public void setDateFixed(String dateFixed) {
        this.dateFixed = dateFixed;
    }

    public void setFixDescription(String fixDescription) {
        this.fixDescription = fixDescription;
    }
}
