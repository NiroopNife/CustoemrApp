package com.example.customerapp.School;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SchoolService {

    @FormUrlEncoded
    @POST("school_login/")
    Call<SchoolResponse> sendSchoolname(
            @Field("school_id") String schoolName
    );
}
