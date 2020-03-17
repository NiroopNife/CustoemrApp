package com.example.customerapp.ForgotPassword.PhaseTwo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OTPService {

    @FormUrlEncoded
    @POST("update_parentpass")
    Call<OTPResponse> sendreceivedOTP(
            @Field("otp") String receivedOTP,
            @Field("password") String changedPassword
    );
}
