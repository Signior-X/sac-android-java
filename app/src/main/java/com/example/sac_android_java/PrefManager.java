package com.example.sac_android_java;

import java.util.HashMap;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.SharedPreferences.Editor;


/*
    Class for management for Session using Shared Preferences
    @author: Priyam Seth
    @date: 9th April, 2020

 */
public class PrefManager {
    //Shared Preferences
    SharedPreferences pref;

    //Shared Preferences Editor
    SharedPreferences.Editor editor;

    //Context for handling where it is called
    Context _context;

    //shared_pref_mode  ??
    int PRIVATE_MODE = 0;

    //shared_preferences_file_name
    private static final String PREF_NAME = "android-sac-java";    //Changed this according to mine

    //For checking if it is first time launch
    private static final String IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch";

    //For checking a session and storing login details
    private static final String IS_LOGIN = "IsLoggedIn";
    // Make public, so that it can be accessed form outside this class
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    //Constructor
    public PrefManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login Session
     * @param username - To add to the key KEY_USERNAME
     * @param password - To add to the kaye KEY_PASSWORD
     */
    public void createLoginSession(String username, String password){
        //make logged in true
        editor.putBoolean(IS_LOGIN, true);

        //storing username and password in pref
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);

        //Commit changes - Never Forget this !Important
        editor.commit();
    }

    /**
     *  Function to get the session data in the form of Hash Map
     * @return Hashmap of userdetails
     */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();

        //Putting the userdata from pref to HashMap
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        return user;
    }

    /**
     * Function to check if it is Logged in
     * @return true if loggedIn
     */
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Function to check if the user is not logged in or not
     * !Important
     * It false it will redirect user to login Page
     * Else It won't do anything
     */
    public void checkLogin(){
        //Check Login Status
        if(!this.isLoggedIn()){
            //User is not logged in, so redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            //Closing all the activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //Add new Flag to start a new activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Start Login Activity
            _context.startActivity(i);
        }
    }

    /**
     *
     */

    public void LogoutUser(){
        editor.clear();  //Clear the data stored in the shared preferences
        setFirstTimeLaunch(false);   //The WelcomeActivity to not be called everrytimme, so let it store in the data
        editor.commit();
    }

    /*  Commented this as this was having problem.

    public void logoutUser(){
        //Clearing all data from shared prefernces
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    */



    /**
     * Function to set the key IS_FIRST_TIME_LAUNCH as passed in the function
     * @param isFirstTime - To set the key IS_FIRST_TIME_LAUNCH
     */
    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        //Commit changes
        editor.commit();
    }

    /**
     * Function to check if it is first time launched or not
     * @return
     */
    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
