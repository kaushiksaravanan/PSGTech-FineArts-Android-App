package com.psgtech.fineartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventsDetailActivity extends AppCompatActivity {

    // variables
    String eventName;

    // intent
    Intent intent;

    // views
    TextView eventNameView;
    GridView eventFunc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_detail);

        // get intent of string
        intent = getIntent();

        // variables
        eventName = intent.getStringExtra("eventName");

        // set the event Name
        eventNameView = findViewById(R.id.eventName);
        eventNameView.setText(eventName);

        // get the grid layout
        eventFunc = findViewById(R.id.eventFunctions);

        // pass to adaptor
        ArrayList<adminFunctionModel> funcs = new ArrayList<adminFunctionModel>();
        funcs.add(new adminFunctionModel("Attendance", R.drawable.attendance));
        funcs.add(new adminFunctionModel("Gallery", R.drawable.gallery));
        funcs.add(new adminFunctionModel("Resource", R.drawable.resource));
        funcs.add(new adminFunctionModel("Report", R.drawable.report));


        // adaptor
        adminFunctionAdap funcAdap = new adminFunctionAdap(this, funcs);
        eventFunc.setAdapter(funcAdap);

        // listeners
        eventFunc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFunc = funcs.get(position).getFunctionName();
                Intent intent;
                if (selectedFunc.equals("Attendance")){
                    intent = new Intent(EventsDetailActivity.this, EventAttendanceActivity.class);
                    intent.putExtra("eventName", eventName);
                    startActivity(intent);
                }
                else if (selectedFunc.equals("Gallery")) {
                    intent = new Intent(EventsDetailActivity.this, EventGalleryActivity.class);
                    intent.putExtra("eventName", eventName);
                    startActivity(intent);
                }
                else if (selectedFunc.equals("Resource")) {
                    intent = new Intent(EventsDetailActivity.this, EventResourceActivity.class);
                    intent.putExtra("eventName", eventName);
                    startActivity(intent);
                }

                else if (selectedFunc.equals("Report")) {
                    intent = new Intent(EventsDetailActivity.this, EventReportActivity.class);
                    intent.putExtra("eventName", eventName);
                    startActivity(intent);
                }
            }
        });



    }
}