package com.example.lab5_20175947_20191417.retrofit.services;

import com.example.lab5_20175947_20191417.retrofit.RandoMuser;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandoMuserServices {
    @GET("/api/")
    Call<RandoMuser> getRandonMuser();
}
