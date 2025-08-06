package com.psgtech.fineartsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class viewEventsAdapter extends ArrayAdapter {
    List<EventsModel> events;
    int itemLayout;
    Context c;

    public viewEventsAdapter(List<EventsModel> events, int resource, Context context) {
        super(context, resource, events);
        this.events = events;
        this.itemLayout = resource;
        this.c = context;
    }

    public int getCount() {
        return events.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        }
        EventsModel event = events.get(position);
        TextView name = convertView.findViewById(R.id.eventName);
        TextView date = convertView.findViewById(R.id.date);
        TextView incharge = convertView.findViewById(R.id.incharge);

        name.setText(event.getName());
        date.setText(event.getDate());
        incharge.setText(event.getIncharge());

        return convertView;
    }
}
