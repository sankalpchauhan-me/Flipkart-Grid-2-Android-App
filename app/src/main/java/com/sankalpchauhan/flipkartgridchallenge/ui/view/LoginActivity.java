package com.sankalpchauhan.flipkartgridchallenge.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.sankalpchauhan.flipkartgridchallenge.MainActivity;
import com.sankalpchauhan.flipkartgridchallenge.R;
import com.sankalpchauhan.flipkartgridchallenge.config.DefaultPrefSettings;
import com.sankalpchauhan.flipkartgridchallenge.databinding.ActivityLoginBinding;
import com.sankalpchauhan.flipkartgridchallenge.service.model.LoginResponse;
import com.sankalpchauhan.flipkartgridchallenge.viewmodel.LoginActivityViewModel;

import static com.sankalpchauhan.flipkartgridchallenge.util.Utility.isValidEmail;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    boolean isAnimating;
    private LoginActivityViewModel loginActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewModel();
        binding.mobEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                isAnimating = true;
                animate(-1, 0f);
            }
        });

        binding.createAccount.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(i);
        });

        binding.registerFab.setOnClickListener(view -> {
           if(validForm()){
                loginUser();
           }
        });
    }

    private void loginUser() {
        loginActivityViewModel.loginUser(binding.mobEt.getText().toString(), binding.passEt.getText().toString()).observe(this, loginResponse -> {
            if(loginResponse!=null) {
                DefaultPrefSettings.getInstance().saveAuthToken(loginResponse.getToken());
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validForm() {
        if(binding.mobEt.getText().toString().trim().isEmpty()){
            binding.mobEt.setError("Required");
            return false;
        }

        if(binding.passEt.getText().toString().trim().isEmpty()){
            binding.passEt.setError("Required");
            return false;
        }

        if(binding.passEt.getText().toString().length()<=6){
            Toast.makeText(this, "Password should be atleast 6 charachters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!isValidEmail(binding.mobEt.getText().toString())){
            binding.mobEt.setError("Invalid email");
            return false;
        }

        return true;
    }

    private void initViewModel() {
        loginActivityViewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
    }

    private void animate(int direction, float alpha) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float height = metrics.heightPixels-metrics.heightPixels/50.0f;

        FlingAnimation flingX =
                new FlingAnimation(binding.getRoot(), DynamicAnimation.Y)
                        .setStartVelocity(direction*height)
                        .setFriction(0.5f);
        flingX.start();

        FlingAnimation fabX =
                new FlingAnimation(binding.registerFab, DynamicAnimation.Y)
                        .setStartVelocity((-1*direction)*150f)
                        .setFriction(0.5f);
        flingX.start();
        fabX.start();

        binding.bgImg.animate().alpha(alpha).setDuration(500).start();
        if(direction==-1) {
            binding.passEt.setFocusable(true);
            binding.passETHolder.setVisibility(View.VISIBLE);
            binding.passETHolder.animate().alpha(1f).setDuration(500).start();
            binding.createAccount.animate().alpha(0f).setDuration(500).start();
        } else {
            binding.passEt.setFocusable(false);
            binding.passETHolder.animate().alpha(0f).setDuration(500).start();
            binding.createAccount.animate().alpha(1f).setDuration(500).start();

        }

    }

    @Override
    public void onBackPressed() {

        if(!isAnimating){
            super.onBackPressed();
        }
        else{
            animate(1,1f);
            isAnimating=false;
            binding.mobEt.clearFocus();
        }
    }
}
