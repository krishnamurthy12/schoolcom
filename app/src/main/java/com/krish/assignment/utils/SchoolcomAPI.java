package com.krish.assignment.utils;

import com.krish.assignment.api_responses.example_one.ExampleOneResponse;
import com.krish.assignment.api_responses.example_three.ExampleResponseThree;
import com.krish.assignment.api_responses.example_two.ExampleResponseTwo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SchoolcomAPI {

    /*@Headers("Content-Type: application/json")*/

    @GET("everything")
    Call<ExampleOneResponse> callExampleAPIOne(@Query("q") String q,
                                               @Query("from") String from,
                                               @Query("to") String to,
                                               @Query("sortBy") String sortBy,
                                               @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ExampleResponseTwo> callExampleAPITwo(@Query("country") String country,
                                               @Query("category") String category,
                                               @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ExampleResponseThree> callExampleAPIThree(@Query("sources") String sources,
                                                   @Query("apiKey") String apiKey);



}
