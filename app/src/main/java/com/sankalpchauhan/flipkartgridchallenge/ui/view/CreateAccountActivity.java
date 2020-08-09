package com.sankalpchauhan.flipkartgridchallenge.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.sankalpchauhan.flipkartgridchallenge.MainActivity;
import com.sankalpchauhan.flipkartgridchallenge.R;
import com.sankalpchauhan.flipkartgridchallenge.config.DefaultPrefSettings;
import com.sankalpchauhan.flipkartgridchallenge.databinding.ActivityCreateAccountBinding;
import com.sankalpchauhan.flipkartgridchallenge.service.model.SignUpRequest;
import com.sankalpchauhan.flipkartgridchallenge.service.model.SignUpResponse;
import com.sankalpchauhan.flipkartgridchallenge.viewmodel.CreateAccountActivityViewModel;

import static com.sankalpchauhan.flipkartgridchallenge.util.Utility.isValidEmail;

public class CreateAccountActivity extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private CreateAccountActivityViewModel createAccountActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(binding.getRoot());
        initViewModel();
        if (savedInstanceState == null) {
            binding.getRoot().setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = binding.getRoot().getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        binding.getRoot().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }

        binding.displayNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().contains(" ")){
                    binding.displayNameET.setError("Spaces Not Allowed");
                } else {
                    binding.displayNameET.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.registerFab.setOnClickListener(view -> {
            if(validForm()){
                String[] emailDelimeter = binding.mobEt.getText().toString().split("@");
                SignUpRequest signUpRequest = new SignUpRequest(binding.displayNameET.getText().toString(), emailDelimeter[0], binding.mobEt.getText().toString(), binding.passEt.getText().toString());
                registerUser(signUpRequest);
            }
        });
    }

    private void initViewModel() {
        createAccountActivityViewModel = new ViewModelProvider(this).get(CreateAccountActivityViewModel.class);
    }

    private void registerUser(SignUpRequest signUpRequest) {
        binding.registerFab.setVisibility(View.INVISIBLE);
        createAccountActivityViewModel.signUpUser(signUpRequest).observe(this, signUpResponse -> {
            binding.registerFab.setVisibility(View.VISIBLE);
            if(signUpResponse!=null){
                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                loginUser();
            } else {
                Toast.makeText(this, "Something Went Wrong! Please Retry", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser() {
        binding.registerFab.setVisibility(View.INVISIBLE);
        createAccountActivityViewModel.loginUser(binding.mobEt.getText().toString(), binding.passEt.getText().toString()).observe(this, loginResponse -> {
            binding.registerFab.setVisibility(View.VISIBLE);
            if(loginResponse!=null) {
                Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
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

        if(binding.displayNameET.getText().toString().trim().isEmpty()){
            binding.displayNameET.setError("Required");
            return false;
        }

        if(!binding.confPassEt.getText().toString().equals(binding.passEt.getText().toString())){
            binding.confPassEt.setError("Password Do Not Match");
            return false;
        }

        if(binding.displayNameET.getText().toString().contains(" ")){
            binding.displayNameET.setError("Spaces Not Allowed");
            return false;
        }

        return true;
    }

    private void circularRevealActivity() {

        int cx = binding.getRoot().getWidth() / 2;
        int cy = binding.getRoot().getHeight();

        float finalRadius = Math.max(binding.getRoot().getWidth(), binding.getRoot().getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(binding.getRoot(), cx, cy, 0, finalRadius);
        circularReveal.setDuration(1000);

        // make the view visible and start the animation
        binding.getRoot().setVisibility(View.VISIBLE);
        circularReveal.start();
    }
}
