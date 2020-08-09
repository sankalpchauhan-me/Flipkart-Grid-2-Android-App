package com.sankalpchauhan.flipkartgridchallenge.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sankalpchauhan.flipkartgridchallenge.config.DefaultPrefSettings;
import com.sankalpchauhan.flipkartgridchallenge.service.model.LoginResponse;
import com.sankalpchauhan.flipkartgridchallenge.service.model.SignUpRequest;
import com.sankalpchauhan.flipkartgridchallenge.service.model.SignUpResponse;
import com.sankalpchauhan.flipkartgridchallenge.service.repository.BackendRepository;

public class CreateAccountActivityViewModel extends AndroidViewModel {
    private BackendRepository backendRepository;

    public CreateAccountActivityViewModel(@NonNull Application application) {
        super(application);
        backendRepository = BackendRepository.getInstance();
    }

    public LiveData<SignUpResponse> signUpUser(SignUpRequest signUpRequest){
        return backendRepository.signUp(signUpRequest);
    }

    public LiveData<LoginResponse> loginUser(String email, String password){
        DefaultPrefSettings.getInstance().putUserEmail(email);
        return backendRepository.login(email, password);
    }
}
