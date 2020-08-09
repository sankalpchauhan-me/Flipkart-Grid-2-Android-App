package com.sankalpchauhan.flipkartgridchallenge.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

public class DefaultPrefSettings {
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private static DefaultPrefSettings ourInstance = new DefaultPrefSettings();
    private SharedPreferences defaultPref;
    private final Object object = new Object();
    private final String AUTH_TOKEN = "token";
    private final String IS_FIRST_TIME = "firsttime";
    private final String DARK_MODE = "d";
    private final String USER_EMAIL = "email";

    private DefaultPrefSettings() {
    }

    public static DefaultPrefSettings getInstance() {
        return ourInstance;
    }

    public static void init(Context context) {
        ourInstance.defaultPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveAuthToken(String value){
        synchronized (object){
            SharedPreferences.Editor editor = defaultPref.edit();
            editor.putString(AUTH_TOKEN, value);
            editor.apply();
        }
    }

    public String fetchAuthToken(){
        synchronized (object){
            return defaultPref.getString(AUTH_TOKEN, null);
        }
    }

    public void setUserFirstTime(Boolean value){
        synchronized (object){
            SharedPreferences.Editor editor = defaultPref.edit();
            editor.putBoolean(IS_FIRST_TIME, value);
            editor.apply();
        }
    }

    public boolean isUserFirstTime(){
        synchronized (object){
            return defaultPref.getBoolean(IS_FIRST_TIME, true);
        }
    }

    public void enableDarkMode(Boolean value){
        synchronized (object) {
            SharedPreferences.Editor editor = defaultPref.edit();
            editor.putBoolean(DARK_MODE, value);
            editor.apply();
        }
    }

    public boolean isDarkMode(){
        synchronized (object){
            return defaultPref.getBoolean(DARK_MODE, false);
        }
    }

    public void putUserEmail(String email){
        synchronized (object){
            SharedPreferences.Editor editor = defaultPref.edit();
            editor.putString(USER_EMAIL, email);
            editor.apply();
        }
    }

    public String getUserEmail(){
        synchronized (object){
            return defaultPref.getString(USER_EMAIL, null);
        }
    }
}
