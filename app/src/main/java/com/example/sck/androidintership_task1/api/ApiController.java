package com.example.sck.androidintership_task1.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    // create a converter compatible with Realm
    // GSON can parse the data.
    // Note there is a bug in GSON 2.5 that can cause it to StackOverflow when working with RealmObjects.
    // To work around this, use the ExclusionStrategy below or downgrade to 1.7.1
    // See more here: https://code.google.com/p/google-gson/issues/detail?id=440
    private static final Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    // Builder API which allows defining the URL endpoint for the HTTP operation
    private static final Retrofit.Builder RETROFIT = new Retrofit.Builder()
            .baseUrl(ApiConst.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static ApiService getApiService() {
        return RETROFIT.build().create(ApiService.class);
    }
}