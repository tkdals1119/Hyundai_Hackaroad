package com.sourcey.Hackaroad.service;


import com.google.gson.JsonObject;
import com.sourcey.Hackaroad.model.Driver;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by BSM on 2017-10-25.
 */

public interface ServerApi {
    @POST("/drivers")
    Call<Driver> signUpDriver(@Body JsonObject driver);

    //test1
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://hyundaiserver-sangm2n.c9users.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
