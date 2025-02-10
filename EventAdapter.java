package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.List;

public class EventAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> events;
    private OnDeleteListener onDeleteListener;


    public EventAdapter(Context context, List<String> events,OnDeleteListener onDeleteListener) {
        super(context, 0, events);
        this.context = context;
        this.events = events;
        this.onDeleteListener = onDeleteListener;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false);
        }

        TextView eventText = convertView.findViewById(R.id.eventText);
        ImageView deleteIcon = convertView.findViewById(R.id.deleteButton);

        eventText.setText(events.get(position));

        deleteIcon.setOnClickListener(v -> {
            // Call the listener to delete the event
            if (onDeleteListener != null) {
                onDeleteListener.onDelete(position);
            }
        });

        return convertView;
    }
    public interface OnDeleteListener {
        void onDelete(int position);
    }
}
