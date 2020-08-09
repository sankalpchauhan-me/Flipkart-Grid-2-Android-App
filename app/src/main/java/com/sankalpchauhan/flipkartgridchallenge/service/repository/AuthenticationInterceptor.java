package com.sankalpchauhan.flipkartgridchallenge.service.repository;

import com.sankalpchauhan.flipkartgridchallenge.config.DefaultPrefSettings;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        if(original.url().encodedPath().contains("/login")&&original.method().equals("post") || original.url().encodedPath().contains("/signup")&&original.method().equals("post")){
            return chain.proceed(original);
        }
        Request.Builder builder = original.newBuilder()
                .header("Authorization", "Bearer "+DefaultPrefSettings.getInstance().fetchAuthToken());

        Request request = builder.build();
        return chain.proceed(request);
    }
}