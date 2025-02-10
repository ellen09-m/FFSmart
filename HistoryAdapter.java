package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MaintenanceViewHolder> {
    private List<MaintenanceHistory> maintenanceList;

    public HistoryAdapter(List<MaintenanceHistory> maintenanceList) {
        this.maintenanceList = maintenanceList;
    }

    @Override
    public MaintenanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_history, parent, false);
        return new MaintenanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MaintenanceViewHolder holder, int position) {
        MaintenanceHistory record = maintenanceList.get(position);
        holder.maintenanceTypeTextView.setText(record.getMaintenanceType());
        holder.dateTextView.setText(record.getDate());
        holder.descriptionTextView.setText(record.getDescription());
    }

    @Override
    public int getItemCount() {
        return maintenanceList.size();
    }

    public static class MaintenanceViewHolder extends RecyclerView.ViewHolder {
        TextView maintenanceTypeTextView;
        TextView dateTextView;
        TextView descriptionTextView;

        public MaintenanceViewHolder(View itemView) {
            super(itemView);
            maintenanceTypeTextView = itemView.findViewById(R.id.maintenance_type);
            dateTextView = itemView.findViewById(R.id.date);
            descriptionTextView = itemView.findViewById(R.id.description);
        }
    }
}
