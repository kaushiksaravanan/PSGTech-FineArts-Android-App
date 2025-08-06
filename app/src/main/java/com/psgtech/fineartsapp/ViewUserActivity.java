package com.psgtech.fineartsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class ViewUserActivity extends AppCompatActivity {

    RecyclerView userView;
    viewUserAdap userAdap;
    ArrayList<UserModel> userList;

    // progress dialog when fetching the data
    ProgressDialog progressDialog;

    // Display metrics
    DisplayMetrics metrics;

    // firebase
    FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        // progress dialog when fetching the data
        // Todo progress dialog need to customize
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait\nFetching Data...");
        progressDialog.show();

        //get views
        userView = findViewById(R.id.userView);

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
        userAdap = new viewUserAdap(ViewUserActivity.this, userList);
        userView.setAdapter(userAdap);


        // onclick function



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