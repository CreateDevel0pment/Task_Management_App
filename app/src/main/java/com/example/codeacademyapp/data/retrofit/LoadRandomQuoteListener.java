package com.example.codeacademyapp.data.retrofit;

import com.example.codeacademyapp.data.model.Quote;

public interface LoadRandomQuoteListener {

    void onSuccess(Quote quote);

    void onError(Exception e);

}
