package com.example.customerapp.ForgotPassword.PhaseTwo;

import com.example.customerapp.Constants.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPClient {

    private static Retrofit retrofit = null;

    private OTPClient(){}

    public static Retrofit forgotSecond(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
