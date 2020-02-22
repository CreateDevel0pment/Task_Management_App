package com.example.codeacademyapp.data.retrofit;

import com.example.codeacademyapp.data.model.Quote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuotesApi {

    @GET("/quotes/random")
    Call<Quote> getRandomQuote();


}
