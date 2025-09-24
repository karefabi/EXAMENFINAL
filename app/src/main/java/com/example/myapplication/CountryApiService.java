package com.example.myapplication;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CountryApiService {
    @GET("v3.1/name/{country}")
    Call<List<CountryResponse>> getCountryInfo(@Path("country") String country);
}
