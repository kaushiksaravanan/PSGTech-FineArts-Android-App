package com.psgtech.fineartsapp;

import static com.google.api.ResourceProto.resource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class AddUserActivity extends AppCompatActivity {

    // views
    TextInputLayout nameLayout, passwordLayout, phoneNumberLayout, emailLayout, deptLayout,
                    yearLayout, roleLayout;
    Button addButton; // button for login page
    AutoCompleteTextView deptDropDown, roleDropDown;
    RadioGroup adminPrivRadGroup;
    RadioButton adminPrivRadYes, adminPrivRadNo;
    // for adapters
    String[] roleName, departmentName;

    // firebase
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        // get instance for firebase
        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        // initialize the view
        nameLayout = findViewById(R.id.name);
        passwordLayout = findViewById(R.id.password);
        phoneNumberLayout = findViewById(R.id.phoneNo);
        emailLayout = findViewById(R.id.email);
        deptLayout = findViewById(R.id.deptLayout);
        yearLayout = findViewById(R.id.yearLayout);
        roleLayout = findViewById(R.id.roleLayout);
        addButton = findViewById(R.id.addButton);
        adminPrivRadGroup = findViewById(R.id.adminPrivRadGroup);
        adminPrivRadNo = findViewById(R.id.adminPrivRadNo);
        adminPrivRadYes = findViewById(R.id.adminPrivRadYes);

        // dropdown view with auto complete
        deptDropDown = findViewById(R.id.deptDropDown);
        roleDropDown = findViewById(R.id.roleDropDown);

        // department adapter
        departmentName = getResources().getStringArray(R.array.departments);
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(this, R.layout.drop_down_textview,
                departmentName);
        deptDropDown.setAdapter(departmentAdapter);

        // role adapter
        roleName = getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(this, R.layout.drop_down_textview,
                roleName);
        roleDropDown.setAdapter(roleAdapter);

        // listerners
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameLayout.getEditText().getText().toString();
                String year = yearLayout.getEditText().getText().toString();
                String phoneno = phoneNumberLayout.getEditText().getText().toString();
                String password = phoneNumberLayout.getEditText().getText().toString();
                String role = roleLayout.getEditText().getText().toString();
                String dept = deptLayout.getEditText().getText().toString();
                String email = emailLayout.getEditText().getText().toString();
                String roleNo = email.split("@")[0];
                boolean adminPriv;
                int radid = adminPrivRadGroup.getCheckedRadioButtonId();
                if (radid == R.id.adminPrivRadNo) {
                    adminPriv = false;
                }
                else {
                    adminPriv = true;
                }

                if (!validName(name) | !validPassword(password) | !validEmail(email) | !validRole(role) |
                    !validDeptName(dept) | !validPhoneNo(phoneno) | !validYear(year)){return;}
                System.out.println(name + " " + year + " " + role + " " + dept + " " + adminPriv + " " + phoneno);
                fireStore.collection("user")
                        .document(email)
                        .set(new UserModel(name, roleNo, role, dept, phoneno, email, year, adminPriv));
                // create a new user
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(AddUserActivity.this, new OnCompleteListener<AuthResult>(){
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    Snackbar.make(v, "Successfully Created", Snackbar.LENGTH_LONG).show();
                                    // Todo: need to modify this code for user friendly
                                    firebaseAuth.signOut();
                                    Intent intent = new Intent(AddUserActivity.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Snackbar.make(v, "Failed to Created. Try Again", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    public boolean validName(String name){
        if (name.isEmpty()) {
            nameLayout.setError("Field cannot be empty");
            return false;
        }
        else{
            nameLayout.setError(null);
            nameLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validEmail(String email){
        String match = "^(.+)@(.+)$";
        if (email.isEmpty()) {
            emailLayout.setError("Field cannot be empty");
            return false;
        }
        else if (!email.matches(match)){
            // check whether the email was valid
            emailLayout.setError("Invalid Email");
//            emailLayout.setHelperText("check whether the email was correct");
            return false;
        }
        else{
            emailLayout.setError(null);
            emailLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validPhoneNo(String phone){
        if (phone.isEmpty()) {
            phoneNumberLayout.setError("Field cannot be empty");
            return false;
        }
        else if (phone.length() != 10) {
            phoneNumberLayout.setError("Invalid Phone No");
        }
        else{
            phoneNumberLayout.setError(null);
            phoneNumberLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validPassword(String password){
        if (password.isEmpty()) {
            passwordLayout.setError("Field cannot be empty");
            return false;
        }
        else if (password.length() < 6){
            passwordLayout.setError("Password should be at least 6 letters");
        }
        else{
            passwordLayout.setError(null);
            passwordLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validYear(String year) {
        String[] years = {"1", "2", "3", "4", "5"};
        if (year.isEmpty()) {
            yearLayout.setError("Field cannot be empty");
        }
        else if (!Arrays.asList(years).contains(year)){
            yearLayout.setError("Year should be 1 to 5");
        }
        else {
            yearLayout.setError(null);
            yearLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validRole(String role) {
        if (role.isEmpty()){
            roleLayout.setError("Field cannot be empty");
        }
        else if (!Arrays.asList(roleName).contains(role)) {
            roleLayout.setError("Role should be choose from Drop down list");
        }
        else {
            roleLayout.setError(null);
            roleLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validDeptName(String dept){
        if (dept.isEmpty()){
            deptLayout.setError("Field cannot be empty");
        }
        else if (!Arrays.asList(departmentName).contains(dept)){
            deptLayout.setError("Role should be choose from Drop down list");
        }
        else {
            deptLayout.setError(null);
            deptLayout.setErrorEnabled(false);
        }
        return true;
    }
}