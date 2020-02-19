package com.example.codeacademyapp.retrofit;

import com.example.codeacademyapp.data.model.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuotesApi {

    @GET("/quotes/random")
    Call<Quote> getRandomQuote();


}
