package com.example.customerapp.Profile;

import com.example.customerapp.Constants.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileClient {

    private static Retrofit retrofit = null;

    private ProfileClient(){}

    public static Retrofit sendProfileDetails(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
