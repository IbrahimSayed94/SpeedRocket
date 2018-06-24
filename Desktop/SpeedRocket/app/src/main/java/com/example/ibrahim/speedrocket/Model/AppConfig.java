package com.example.ibrahim.speedrocket.Model;

import com.google.gson.Gson;

import retrofit.RxJavaCallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Ibrahim on 5/2/2018.
 */

public class AppConfig
{


    private static String BASE_URL = "https://speed-rocket.com/api/";
    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
} // class AppConfiq
