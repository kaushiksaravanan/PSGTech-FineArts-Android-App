package com.psgtech.fineartsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;

public class adminFunctionAdap extends ArrayAdapter<adminFunctionModel> {

    public adminFunctionAdap(@NonNull Context context, ArrayList<adminFunctionModel> funcList) {
        super(context, 0, funcList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.admin_function_card, parent, false);
        }
        adminFunctionModel func = getItem(position);
        TextView funcName = listitemView.findViewById(R.id.funcName);
        ShapeableImageView funcImg = listitemView.findViewById(R.id.funcBut);
        funcName.setText(func.getFunctionName());
        funcImg.setImageResource(func.getImgId());
        return listitemView;

    }
}
