package com.example.sac_android_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Defining the elements
    Button logoutButton;
    String sessionUsername, sessionPassword;
    TextView session_username, session_password;

    // PrefManager for handling the sessions
    PrefManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Getting the session as PrefManager object
        session = new PrefManager(getApplicationContext());

        // Checking that the a user is login
        session.checkLogin();

        // Getting the UserDetails
        HashMap<String, String > details = new HashMap<>();
        details = session.getUserDetails();
        sessionUsername = details.get(PrefManager.KEY_USERNAME);
        sessionPassword = details.get(PrefManager.KEY_PASSWORD);

        // Getting the Text Boxes
        session_username = (TextView) findViewById(R.id.session_username);
        session_password = (TextView) findViewById(R.id.session_password);
        //Setting the data in Text Boxes
        session_username.setText(sessionUsername);
        session_password.setText(sessionPassword);

        // Logout Button On Click Action
        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.LogoutUser();  // Logging Out User
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
