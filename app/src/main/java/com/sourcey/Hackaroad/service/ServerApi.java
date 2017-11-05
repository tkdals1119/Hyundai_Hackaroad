package com.sourcey.Hackaroad.service;


import com.google.gson.JsonObject;
import com.sourcey.Hackaroad.model.Case_List;
import com.sourcey.Hackaroad.model.Driver;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by BSM on 2017-10-25.
 */

public interface ServerApi {
    @POST("/loginusers")
    Call<Driver> signUpDriver(@Body JsonObject driver);

    //학생 중복 검사
    @GET("/driver/check_duplicate")
    Call<ResponseBody> checkDuplicateDriver(	 @Query("userid") String userid,
                                                 @Query("userpw") String userpw);

    @GET("/driver/{userid}/get_name")
    Call<Driver> getDriver(@Path("userid") String userid);

    @GET("/driver/get_list")
    Call<List<Case_List>> getList();



    //test1
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://hyundaiserver-sangm2n.c9users.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
