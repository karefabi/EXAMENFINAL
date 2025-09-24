package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserApiService {
    @GET("api/")
    Call<UserResponse> getUsers(@Query("results") int count);
}
