package com.example.ahmedjava;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/nameupdate")
    Call<Void> executeupdate(@Body HashMap<String, String> map);

}