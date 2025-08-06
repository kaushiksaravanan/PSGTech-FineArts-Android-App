package com.psgtech.fineartsapp;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class viewUserAdap extends RecyclerView.Adapter<viewUserAdap.userViewHolder> {

    // todo need to add image if image is not set it show boy photo for boys and girl photo for girl
    // the above feature require gender

    Context context;
    ArrayList<UserModel> userList;

    SparseBooleanArray selectedUser = new SparseBooleanArray();
    // constructor
    public viewUserAdap(Context context, ArrayList<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }
    
    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflate the view_card_user layout for changing the values in the layout
        View view = LayoutInflater.from(context).inflate(R.layout.view_user_card, parent, false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {

        UserModel user = userList.get(position);

        // set the text for the userName, role and dept
        holder.userName.setText(user.getName());
        holder.roleNo.setText(user.getRoleNo());
        holder.role.setText(user.getRole());
        holder.dept.setText(user.getDept());

        // year is set based on the number
        String year = user.getYear();
        String set;
        if (year.equals("1")){
            set = "First Year";
        }
        else if (year.equals("2")){
            set = "Second Year";
        }
        else if (year.equals("3")){
            set = "Third Year";
        }
        else if (year.equals("4")){
            set = "Fourth Year";
        }
        else {
            set = "Fifth Year";
        }

        // set the year
        holder.year.setText(set);

        // onClick
        holder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = selectedUser.get(position, false);
                selectedUser.put(position, !selected);
                int color = selected ? context.getResources().getColor(R.color.white) : context.getResources().getColor(R.color.nude);
                holder.user.setCardBackgroundColor(color);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public SparseBooleanArray getSelectedUser() {
        return selectedUser;
    }

    public static class userViewHolder extends RecyclerView.ViewHolder{
        TextView userName, dept, year, role, roleNo;
        CardView user;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);

            // getting the views
            user = itemView.findViewById(R.id.user);
            userName = itemView.findViewById(R.id.userName);
            roleNo = itemView.findViewById(R.id.roleNo);
            role = itemView.findViewById(R.id.role);
            dept = itemView.findViewById(R.id.dept);
            year = itemView.findViewById(R.id.year);
        }

    }
}
