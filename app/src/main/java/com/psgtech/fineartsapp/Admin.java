package com.psgtech.fineartsapp;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.shape.MaterialShapeDrawable;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    GridView adminFunc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // get the grid layout
        adminFunc = findViewById(R.id.adminFunctions);

        // pass to adaptor
        ArrayList<adminFunctionModel> funcs = new ArrayList<adminFunctionModel>();
        funcs.add(new adminFunctionModel("add Users", R.drawable.add_user));
        funcs.add(new adminFunctionModel("View Users", R.drawable.view_user));
        funcs.add(new adminFunctionModel("Events", R.drawable.event));
        funcs.add(new adminFunctionModel("Admin Privilage", R.drawable.give_privilage));
        funcs.add(new adminFunctionModel("Profile", R.drawable.user));


        // adaptor
        adminFunctionAdap funcAdap = new adminFunctionAdap(this, funcs);
        adminFunc.setAdapter(funcAdap);

        // listeners
        adminFunc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFunc = funcs.get(position).getFunctionName();
                Intent intent;
                if (selectedFunc.equals("add Users")){
                    intent = new Intent(Admin.this, AddUserActivity.class);
                    startActivity(intent);
                }
                else if (selectedFunc.equals("View Users")) {
                    intent = new Intent(Admin.this, ViewUserActivity.class);
                    startActivity(intent);
                }
                else if (selectedFunc.equals("Events")) {
                    intent = new Intent(Admin.this, EventsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}