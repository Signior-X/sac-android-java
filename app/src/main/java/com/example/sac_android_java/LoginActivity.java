package com.example.sac_android_java;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;    // !Important - toast for adding view
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    //Some variables for use
    boolean is_password_hidden=true;
    EditText txtUsername, txtPassword;  //Username and Password
    ImageView show_password;   //Image View button show_password
    Button btnLogin;  //Button lgnButton
    TextView forgot_password, register_user;

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

        //show_password Image View
        show_password = (ImageView) findViewById(R.id.show_password);

        // Login Button
        btnLogin = (Button) findViewById(R.id.btn_login);


        // For showing or hiding Password
        show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_password_hidden) {

                    show_password.setImageResource(R.drawable.ic_eye_off);   //Changing the image button icon
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); // show password
                    is_password_hidden=false;
                }
                else{
                    show_password.setImageResource(R.drawable.ic_eye);   //Changing the image button icon
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance()); // hide password
                    is_password_hidden=true;
                }
            }
        });

        // Forgot Password
        forgot_password = (TextView) findViewById(R.id.help_sign_in);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(i);
            }
        });

        // Register
        register_user = (TextView) findViewById(R.id.register_text);
        register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(i);
            }
        });

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
                            session.createLoginSession(username, password);

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
                        alert.showAlertDialog(LoginActivity.this, "Password Error!", "Password must be atleast of 4 digits", false);
                    }
                }
                else{
                    // Implies Username textview is empty
                    alert.showAlertDialog(LoginActivity.this, "Username Error!", "Username can't be empty", false);
                }
            }
        });
    }

}
