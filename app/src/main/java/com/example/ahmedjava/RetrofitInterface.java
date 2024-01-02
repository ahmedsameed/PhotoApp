package com.example.ahmedjava;

import android.net.Uri;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/nameupdate")
    Call<Void> executeupdate(@Body HashMap<String, String> map);

    @POST("/imageupdate")
    Call<Void> imageupdate(@Body HashMap<String, String> imageMap);

}
