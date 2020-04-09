package com.example.sac_android_java;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    //shared_pref_mode
    int PRIVATE_MODE = 0;

    //shared_preferences_file_name
    private static final String PREF_NAME = "android-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch";

    public PrefManager(Context context){     //Constructor
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
