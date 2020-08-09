package com.sankalpchauhan.flipkartgridchallenge.util;

import androidx.appcompat.app.AppCompatDelegate;

public class DarkModeHandler {
    private static final String lightMode  = "light";
    private static final String darkMode = "dark";
    private static final String batterySaver= "battery";

    public static void themeHelper(Boolean isDarkMode){
        if(!isDarkMode){
            applyTheme(lightMode);
        } else {
            applyTheme(darkMode);
        }
    }
    private static void applyTheme(String theme){
        switch (theme){
            case lightMode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case darkMode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case batterySaver:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

}
