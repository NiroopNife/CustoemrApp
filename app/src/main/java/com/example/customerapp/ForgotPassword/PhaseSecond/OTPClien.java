package com.example.customerapp.ForgotPassword.PhaseSecond;

import com.example.customerapp.Constants.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPClien {

    private static Retrofit retrofit = null;

    private OTPClien(){}

    public static Retrofit forgetSecond(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
