package com.psgtech.fineartsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EventAttendanceActivity extends AppCompatActivity {

    // variables
    String eventName;

    // intent
    Intent intent;

    // views
    TextView eventNameView;
    RecyclerView userView;

    //button
    Button submit;

    // adapter
    viewUserAdap userAdap;
    ArrayList<UserModel> userList;

    // progress dialog when fetching the data
    ProgressDialog progressDialog;

    // firebase
    FirebaseFirestore fireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_attendance);

        // get intent
        intent = getIntent();

        // get eventName
        eventName = intent.getStringExtra("eventName");

        // set event name in view
        eventNameView = findViewById(R.id.eventName);
        eventNameView.setText(eventName + " Attendance");

        // progress dialog when fetching the data
        // Todo progress dialog need to customize
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait\nFetching Data...");
        progressDialog.show();

        //get views
        userView = findViewById(R.id.userView);
        submit = findViewById(R.id.submit);
        // firebase
        fireStore = FirebaseFirestore.getInstance();

        // Todo change the no of coloums based on the width of the screen

        // Layout for recycler view
        LinearLayoutManager userViewLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // recycle view
        userView.setLayoutManager(userViewLayout);
        userView.setHasFixedSize(true);

        // getting data from firestore
        userList = new ArrayList<UserModel>();

        // setting the adapter
        userAdap = new viewUserAdap(EventAttendanceActivity.this, userList);
        userView.setAdapter(userAdap);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray selectedUser = userAdap.getSelectedUser();
                HashMap<String, List<String>> update = new HashMap<>();
                List<String> user = new ArrayList<>();
                for(int i = 0; i < selectedUser.size(); i++) {
                    int key = selectedUser.keyAt(i);
                    if (selectedUser.get(key)) {
                        user.add(userList.get(key).getEmail());
                    }
                }
                System.out.println(user);

                update.put(LocalDate.now().toString(), user);
                fireStore.collection("event_attendance")
                        .document(eventName)
                        .set(update, SetOptions.merge());
            }
        });

        EventChangeListener();
    }

    private void EventChangeListener() {
        fireStore.collection("user")
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
                                String name = (String) dc.getDocument().get("name");
                                // the main admin account not shown in users
                                if (!name.equals("admin")) {
                                    userList.add(dc.getDocument().toObject(UserModel.class));
                                }
                            }
                            userAdap.notifyDataSetChanged();


                            if (progressDialog.isShowing()) {
                                // when error occur dismiss the progress
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}