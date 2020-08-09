package com.sankalpchauhan.flipkartgridchallenge.service.repository;

import androidx.lifecycle.MutableLiveData;

import com.sankalpchauhan.flipkartgridchallenge.config.DefaultPrefSettings;
import com.sankalpchauhan.flipkartgridchallenge.service.model.LoginResponse;
import com.sankalpchauhan.flipkartgridchallenge.service.model.ParsedInvoice;
import com.sankalpchauhan.flipkartgridchallenge.service.model.SignUpRequest;
import com.sankalpchauhan.flipkartgridchallenge.service.model.SignUpResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class BackendRepository {
    public static BackendRepository backendRepository;
    private BackendAPI backendAPI;

    public BackendRepository(){
        backendAPI = RetrofitService.createService(BackendAPI.class, DefaultPrefSettings.getInstance().fetchAuthToken());
    }

    public static BackendRepository getInstance(){
        if(backendRepository==null){
            backendRepository = new BackendRepository();
        }
        return backendRepository;
    }

    public MutableLiveData<SignUpResponse> signUp(SignUpRequest signUpRequest){
        final MutableLiveData<SignUpResponse> signUpResponseMutableLiveData = new MutableLiveData<>();
        backendAPI.signUpUser(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful() && response.code()<300 && response.code()>=200){
                    if(response.body().getStatus()!=null) {
                        signUpResponseMutableLiveData.setValue(response.body());
                    } else {
                        signUpResponseMutableLiveData.setValue(null);
                    }
                }else {
                    signUpResponseMutableLiveData.setValue(null);
                    Timber.d("API Request failed with error" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                t.printStackTrace();
                signUpResponseMutableLiveData.setValue(null);
            }
        });
        return signUpResponseMutableLiveData;
    }

    public MutableLiveData<LoginResponse> login(String email, String password){
        final MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        backendAPI.loginUser(map).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.code()<300 && response.code()>=200){
                    if(response.body().getStatus()!=null) {
                        loginResponseMutableLiveData.setValue(response.body());
                    } else {
                        loginResponseMutableLiveData.setValue(null);
                    }
                } else {
                    loginResponseMutableLiveData.setValue(null);
                    Timber.d("API Request failed with error" + response.code() + " " + response.message());
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                loginResponseMutableLiveData.setValue(null);
            }
        });

        return loginResponseMutableLiveData;
    }

    public MutableLiveData<ParsedInvoice> postInvoice(String url){
        final MutableLiveData<ParsedInvoice> parsedInvoiceMutableLiveData = new MutableLiveData<>();
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        backendAPI.postInvoice(map).enqueue(new Callback<ParsedInvoice>() {
            @Override
            public void onResponse(Call<ParsedInvoice> call, Response<ParsedInvoice> response) {
                if(response.isSuccessful() && response.code()<300 && response.code()>=200){
                    parsedInvoiceMutableLiveData.setValue(response.body());
                } else {
                    parsedInvoiceMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ParsedInvoice> call, Throwable t) {
                t.printStackTrace();
                parsedInvoiceMutableLiveData.setValue(null);
            }
        });

        return parsedInvoiceMutableLiveData;
    }

}
