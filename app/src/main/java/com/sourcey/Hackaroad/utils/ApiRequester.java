package com.sourcey.Hackaroad.utils;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sourcey.Hackaroad.LoginActivity;
import com.sourcey.Hackaroad.model.Driver;
import com.sourcey.Hackaroad.service.ServerApi;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BSM on 2017-10-25.
 */

public class ApiRequester {

    ServerApi driverServerApi;
    Gson gson;
    JsonParser parser;
    Callback callback;


    public volatile static ApiRequester apiRequester;

    public static ApiRequester getInstance() {
        if(apiRequester == null){
            apiRequester = new ApiRequester();
        }
        return apiRequester;
    }


    public interface UserCallback<T> {
        void onSuccess(T result);
        void onFail();
    }

        private class ObjectCallback<T> implements Callback<T>{

            UserCallback callback;

            public ObjectCallback(UserCallback<T> _callback){
                callback = _callback;
            }

            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                // TODO Auto-generated method stub
                if (response.isSuccessful()) {
                    // tasks available
                    callback.onSuccess(response.body());
                } else {
                    int status = response.code();
                    System.out.println(response.message());
                    if( status == 404 ){
                        callback.onSuccess(null);
                    }
                    else {
                        System.out.println("서버 실패");
                        Toast.makeText(new LoginActivity().getApplicationContext(), "서버연결이 되지 않습니다. 문의바랍니다.", Toast.LENGTH_SHORT).show();
                        callback.onFail();
                    }
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                // TODO Auto-generated method stub
                System.out.println("onFailure"+  t.getMessage());
                Toast.makeText(new LoginActivity().getApplicationContext(), "서버연결이 되지 않습니다. 문의바랍니다.", Toast.LENGTH_SHORT).show();
                callback.onFail();
            }
        }

    private class ResultCallback implements Callback<okhttp3.ResponseBody> {

        UserCallback callback;

        public ResultCallback(UserCallback _callback) {
            callback = _callback;
        }

        @Override
        public void onResponse(Call<okhttp3.ResponseBody> call, Response<okhttp3.ResponseBody> response) {
            // TODO Auto-generated method stub
            if (response.isSuccessful()) {
                // tasks available
                JsonObject object;
                try {
                    object = new JsonParser().parse(response.body().string()).getAsJsonObject();
                    callback.onSuccess(object.get("result").getAsBoolean());
                } catch (JsonSyntaxException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
                // error response, no access to resource?
                int status = response.code();
                System.out.println(response.message());
                if (status == 404) {
                    callback.onSuccess(false);
                } else {
                    System.out.println("서버 실패");
                    Toast.makeText(new LoginActivity().getApplicationContext(), "서버연결이 되지 않습니다. 문의바랍니다.", Toast.LENGTH_SHORT).show();
                    callback.onFail();
                }
            }
        }

        @Override
        public void onFailure(Call<okhttp3.ResponseBody> call, Throwable t) {
            // TODO Auto-generated method stub
            System.out.println(t.getMessage());
            Toast.makeText(new LoginActivity().getApplicationContext(), "서버연결이 되지 않습니다. 문의바랍니다.", Toast.LENGTH_SHORT).show();
            callback.onFail();
        }
    }
    private ApiRequester() {
        parser = new JsonParser();
        gson = new Gson();
        driverServerApi = ServerApi.retrofit.create(ServerApi.class);
    }

    //운전자 회원 가입
    public void signUpDriver(Driver driver, UserCallback<Driver> userCallback){
        Call<Driver> call = driverServerApi.signUpDriver(parser.parse(gson.toJson(driver)).getAsJsonObject());
        call.enqueue(new ObjectCallback<Driver>(userCallback));
    }

    // 운전자 등록 여부 확인
    public void checkDuplicateDriver(Driver driver, UserCallback<Boolean> userCallback){
        Call<okhttp3.ResponseBody> call = driverServerApi.checkDuplicateDriver(driver.getLoginid(), driver.getPassword());
        call.enqueue(new ResultCallback(userCallback));
    }

    // 운전자 정보 가져오기
    public void getDriver(String userid, UserCallback<Driver> userCallback){
        Call<Driver> call = driverServerApi.getDriver(userid);
        call.enqueue(new ObjectCallback<Driver>(userCallback));
    }

//    // 운전자 로그인
//    public void loginDriver(Driver driver, UserCallback<Driver> userCallback) {
//        Call<Driver> call = driverServerApi.loginDriver(   "driver",
//                driver.getname(),
//                driver.getLoginid(),
//                driver.getPassword());
//        call.enqueue(new ObjectCallback<Driver>(userCallback));
//    }

}
