package com.example.customerapp.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("parentlogin/")
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password,
            @Field("school_id") String schoolID
    );
}
