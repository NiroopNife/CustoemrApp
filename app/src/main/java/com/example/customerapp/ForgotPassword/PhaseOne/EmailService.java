package com.example.customerapp.ForgotPassword.PhaseOne;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EmailService {

    @FormUrlEncoded
    @POST("parents_forgotpass/")
    Call<EmailResponse> sendRegisteredEmail(
            @Field("email") String regEmail
    );
}
