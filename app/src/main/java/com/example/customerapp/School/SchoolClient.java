package com.example.customerapp.School;

import com.example.customerapp.Constants.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SchoolClient {

    private static Retrofit retrofit = null;

    private SchoolClient(){}

    public static Retrofit sendSchoolname(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
