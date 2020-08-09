package com.sankalpchauhan.flipkartgridchallenge.service.repository;

import com.sankalpchauhan.flipkartgridchallenge.config.Constants;
import com.sankalpchauhan.flipkartgridchallenge.service.model.LoginResponse;
import com.sankalpchauhan.flipkartgridchallenge.service.model.ParsedInvoice;
import com.sankalpchauhan.flipkartgridchallenge.service.model.SignUpRequest;
import com.sankalpchauhan.flipkartgridchallenge.service.model.SignUpResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BackendAPI {
    @POST(Constants.SIGN_UP_ENDPOINT)
    Call<SignUpResponse> signUpUser(@Body SignUpRequest signUpRequest);

    @POST(Constants.SIGN_IN_ENDPOINT)
    Call<LoginResponse> loginUser(@Body Map<String, String> body);

    @POST(Constants.INVOICE_UPLOAD_ENDPOINT)
    Call<ParsedInvoice> postInvoice(@Body Map<String, String> body);

}
