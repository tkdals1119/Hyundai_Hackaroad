package com.sourcey.Hackaroad.service;


import com.google.gson.JsonObject;
import com.sourcey.Hackaroad.model.Driver;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by BSM on 2017-10-25.
 */

public interface ServerApi {
    @POST("/loginusers")
    Call<Driver> signUpDriver(@Body JsonObject driver);

    //학생 중복 검사
    @GET("/driver/check_duplicate")
    Call<ResponseBody> checkDuplicateDriver(	 @Query("loginid") String loginid,
                                                 @Query("password") String password);

    @GET("/driver/get_name")
    Call<Driver> getDriver(@Body JsonObject drvier);



    //test1
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://hyundaiserver-sangm2n.c9users.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
