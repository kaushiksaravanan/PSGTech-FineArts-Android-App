package com.psgtech.fineartsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.imageview.ShapeableImageView;


public class viewUserDialog extends DialogFragment {
    // views
    ShapeableImageView userImage;

    TextView userName, userRollNo, userRole, userPhone, userMail, userPriv,
            userYear, userDept;

    // user information
    UserModel user;

    public viewUserDialog(UserModel user) {
        super();
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.dialog_view_user_detail, container, false);

        // get the views
        userName = view.findViewById(R.id.userName);
        userYear = view.findViewById(R.id.userYear);
        userRollNo = view.findViewById(R.id.userRollNo);
        userDept = view.findViewById(R.id.userDept);
        userRole = view.findViewById(R.id.userRole);
        userImage = view.findViewById(R.id.userPhoto);
        userPhone = view.findViewById(R.id.userPhone);
        userMail = view.findViewById(R.id.userMail);

        return view;
    }
}
