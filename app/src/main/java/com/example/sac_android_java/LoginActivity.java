package com.example.sac_android_java;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;    // !Important - toast for adding view
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    //Username and Password
    EditText txtUsername, txtPassword;

    //Button lgnButton
    Button btnLogin;

    //Alert Dialog box generation
    AlertDialogManager alert = new AlertDialogManager();

    //PrefManager Class - For session
    PrefManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new PrefManager(getApplicationContext());

        //If already logged in, then directly start the MainActivity
        if(session.isLoggedIn()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish(); // To end this activity
        }

        //Username and password fields
        txtUsername = (EditText) findViewById(R.id.et_username);
        txtPassword = (EditText) findViewById(R.id.et_password);

        // Login Button
        btnLogin = (Button) findViewById(R.id.btn_login);

        // On clicking the login Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from edit text
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                //Now check if they are not empty
                if(username.trim().length() > 0){
                    if(password.trim().length()>3){

                        // Here we have to implement the conditions for checking the username and password
                        if(username.equals("test") && password.equals("test")){
                            // username and password are correct
                            // Creating user login session
                            // For testing i am storing dummy data
                            // Use user real data
                            session.createLoginSession("Signior-X", "0911");

                            // Staring MainActivity
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else{
                            // Invalid Credentials
                            alert.showAlertDialog(LoginActivity.this, "Access Denied!", "Invalid Username/Password", false);
                        }
                    }
                    else{
                        // Implies Password textview is empty
                        alert.showAlertDialog(LoginActivity.this, "Access Denied!", "Password must be atleast of 4 digits", false);
                    }
                }
                else{
                    // Implies Username textview is empty
                    alert.showAlertDialog(LoginActivity.this, "Access Denied!", "Username can't be empty", false);
                }
            }
        });
    }

}
