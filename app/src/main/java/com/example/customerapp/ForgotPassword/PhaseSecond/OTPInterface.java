package com.example.customerapp.ForgotPassword.PhaseSecond;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OTPInterface {

    @FormUrlEncoded
    @POST("update_parentpass")
    Call<ResponseOTP> sendOTP(
            @Field("otp") String otpReceived,
            @Field("password") String passwordNew
    );

}
