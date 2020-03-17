package com.example.customerapp.Profile;

import android.content.Intent;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProfileService {

    @FormUrlEncoded
    @POST("update_parent_profile/")
    Call<ProfileResponse> sendProfileDetails(
            @Field("gardian_name") String gardian_name,
            @Field("gardian_phone") String gardian_phone,
            @Field("gardian_address") String gardian_address,
            @Field("parent_id") String parent_id,
            @Field("student_id") String student_id,
            @Field("school_id") String school_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude

    );
}
