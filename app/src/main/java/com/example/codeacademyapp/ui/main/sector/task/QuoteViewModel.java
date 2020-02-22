package com.example.codeacademyapp.ui.main.sector.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.data.model.Quote;
import com.example.codeacademyapp.data.repository.QuoteRepository;
import com.example.codeacademyapp.data.retrofit.LoadRandomQuoteListener;

public class QuoteViewModel extends AndroidViewModel {

    public MutableLiveData<Quote> randomQuote = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public QuoteViewModel(@NonNull Application application) {
        super(application);
    }

    private QuoteRepository repo = QuoteRepository.getInstance(getApplication());


    public void loadRandomQuote(){
        repo.getRandomQuote(new LoadRandomQuoteListener() {
            @Override
            public void onSuccess(Quote quote) {
                randomQuote.setValue(quote);
            }

            @Override
            public void onError(Exception e) {
                error.setValue(e.getMessage());
            }
        });
    }
}
