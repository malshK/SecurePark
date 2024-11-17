package com.example.securepark.api;

import com.example.securepark.models.ApiResponse;
import com.example.securepark.models.LoginRequest;
import com.example.securepark.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("signup/")
    Call<ApiResponse> signup(@Body User user);

    @POST("login/")
    Call<ApiResponse> login(@Body User user);
}
