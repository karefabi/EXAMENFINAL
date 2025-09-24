package com.example.myapplication;

import android.app.Application;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileApp extends Application {
    private static Retrofit randomUserRetrofit;
    private static Retrofit countryRetrofit;
    private static RandomUserApiService randomUserApiService;
    private static CountryApiService countryApiService;

    @Override
    public void onCreate() {
        super.onCreate();

        randomUserRetrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        countryRetrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        randomUserApiService = randomUserRetrofit.create(RandomUserApiService.class);
        countryApiService = countryRetrofit.create(CountryApiService.class);
    }

    public static RandomUserApiService getRandomUserApiService() {
        return randomUserApiService;
    }

    public static CountryApiService getCountryApiService() {
        return countryApiService;
    }
}