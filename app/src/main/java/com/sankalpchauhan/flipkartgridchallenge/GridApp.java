package com.sankalpchauhan.flipkartgridchallenge;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.sankalpchauhan.flipkartgridchallenge.config.DefaultPrefSettings;

import timber.log.Timber;

public class GridApp extends Application {
    private static GridApp instance;
    public static final String CHANNEL_ID = "flipkartgridservice";

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }
        DefaultPrefSettings.init(this);
        createNotificationChannel();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Upload Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
    public static Context getAppContext() {
        return instance;
    }
}
