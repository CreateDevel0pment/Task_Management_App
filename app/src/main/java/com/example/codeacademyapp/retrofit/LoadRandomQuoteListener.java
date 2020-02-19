package com.example.codeacademyapp.retrofit;

import com.example.codeacademyapp.data.model.Quote;

public interface LoadRandomQuoteListener {

    void onSuccess(Quote quote);

    void onError(Exception e);

}
