package com.example.sck.androidintership_task1.api;

import com.example.sck.androidintership_task1.models.IssueDataModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * interface defines the possible HTTP operations
 */
public interface ApiService {

    @GET("tickets")
    Observable<List<IssueDataModel>> loadData(@Query("state") String state,
                                              @Query("amount") int amount);

    @GET("tickets")
    Observable<List<IssueDataModel>> loadData(@Query("state") String state,
                                              @Query("amount") int amount,
                                              @Query("offset") int offcet);
}
