package com.psgtech.fineartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.StackView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    StackView eventView;

    // firebase
    FirebaseFirestore fireStore;
    ArrayList<EventsModel> events;
    // adapter
    viewEventsAdapter eventAdap;

    // progress dialog when fetching the data
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // progress loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait\nFetching Data...");
        progressDialog.show();


        // firebase
        fireStore = FirebaseFirestore.getInstance();

        eventView = findViewById(R.id.eventsView);
        events = new ArrayList<EventsModel>();
        eventAdap = new viewEventsAdapter(events, R.layout.view_event_card, EventsActivity.this);
        eventView.setAdapter(eventAdap);
        eventView.setHapticFeedbackEnabled(true);
        EventChangeListener();

        eventView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String eventName = events.get(position).getName();
                Intent eventDetail = new Intent(EventsActivity.this, EventsDetailActivity.class);
                eventDetail.putExtra("eventName", eventName);
                startActivity(eventDetail);
            }
        });
    }

    private void EventChangeListener() {
        fireStore.collection("events")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    public void onEvent(@NonNull QuerySnapshot value, FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()) {
                                // when error occur dismiss the progress
                                progressDialog.dismiss();
                            }
                            System.out.println("Error: " + error);
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                // the main admin account not shown in users
                                events.add(dc.getDocument().toObject(EventsModel.class));

                            }
                            eventAdap.notifyDataSetChanged();


                            if (progressDialog.isShowing()) {
                                // when error occur dismiss the progress
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}