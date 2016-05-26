package com.example.sck.androidintership_task1.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    // Builder API which allows defining the URL endpoint for the HTTP operation
    private static final Retrofit.Builder RETROFIT = new Retrofit.Builder()
            .baseUrl(ApiConst.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static ApiService getApiService() {
        return RETROFIT.build().create(ApiService.class);
    }
}