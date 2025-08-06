package com.psgtech.fineartsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

public class Login extends AppCompatActivity {
    // views
    Button loginBut;
    ImageView logoImage;
    TextView logoText;
    TextInputLayout emailLayout, passwordLayout;

    // user data
    UserModel user;

    // firebase
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;


    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            String email = firebaseAuth.getCurrentUser().getEmail();
            System.out.println("email: "+ email);
            startActivityByRole(email);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // firebase
        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        // get the views
        logoImage = findViewById(R.id.logo);
        logoText = findViewById(R.id.logoText);
        emailLayout = findViewById(R.id.email); // note it will be email
        passwordLayout = findViewById(R.id.password);
        loginBut = findViewById(R.id.login);


        // listeners
        loginBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                String email = emailLayout.getEditText().getText().toString();
                String password = passwordLayout.getEditText().getText().toString();
                System.out.println(email + " " + password);
                if (!validEmail(email) | !validPassword(password)) {
                    Toast.makeText(Login.this, "Incorrect email or password", Toast.LENGTH_LONG).show();
                }
                else{
                    // sign with the email and password
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
//                                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                                        Snackbar.make(view, "Login Successful", Snackbar.LENGTH_SHORT).show();
                                        // start the new activity corresponding to role
                                        startActivityByRole(email);
                                    } else {
//                                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                                        Snackbar.make(view, "Login Failed", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
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

    private void startActivityByRole(String email) {
        Source source = Source.CACHE;
        fireStore.collection("user")
                .document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(UserModel.class);

                        String role = user.getRole();
                        boolean priv = user.isPriv();
                        // start the new activity based on the role
                        if (role.equals("admin") || priv) {
                            Intent intent = new Intent(Login.this, Admin.class);
                            startActivity(intent);
                            finish();
                        }

                        //TODO: need to create actvity for other roles
                        // code comes here.
                    }
                });
        }
    }