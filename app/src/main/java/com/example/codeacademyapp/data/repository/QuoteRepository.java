package com.example.codeacademyapp.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.data.model.Quote;

import com.example.codeacademyapp.data.retrofit.LoadRandomQuoteListener;
import com.example.codeacademyapp.data.retrofit.QuotesApi;
import com.example.codeacademyapp.data.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteRepository {

    private static QuoteRepository INSTANCE;

    private QuotesApi quotesApi;

    private QuoteRepository() {
    }

    public static QuoteRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new QuoteRepository();
            QuotesApi quotesApi = RetrofitClient.getInstance().getQuotesApi();
            INSTANCE.setQuoteApi(quotesApi);
        }
        return INSTANCE;
    }

    private void setQuoteApi(QuotesApi quotesApi) {
        this.quotesApi = quotesApi;
    }

    public void getRandomQuote(final LoadRandomQuoteListener loadRandomQuoteListener) {

        quotesApi.getRandomQuote().enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                loadRandomQuoteListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {

            }
        });

    }

}
