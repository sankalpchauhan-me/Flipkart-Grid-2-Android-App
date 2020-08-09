package com.sankalpchauhan.flipkartgridchallenge.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sankalpchauhan.flipkartgridchallenge.MainActivity;
import com.sankalpchauhan.flipkartgridchallenge.R;
import com.sankalpchauhan.flipkartgridchallenge.config.DefaultPrefSettings;

import static com.sankalpchauhan.flipkartgridchallenge.util.DarkModeHandler.themeHelper;

public class SplashActivity extends AppCompatActivity {

    Context context = SplashActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        themeHelper(DefaultPrefSettings.getInstance().isDarkMode());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(DefaultPrefSettings.getInstance().isUserFirstTime()) {
                    Intent i = new Intent(context, OnBoardingActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    if(DefaultPrefSettings.getInstance().fetchAuthToken()!=null){
                        Intent i = new Intent(context, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Intent i = new Intent(context, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        }, 2000);
    }
}
