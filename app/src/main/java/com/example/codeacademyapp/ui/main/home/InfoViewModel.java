package com.example.codeacademyapp.ui.main.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.repository.HomePageRepository;
import com.google.firebase.database.DataSnapshot;

public class InfoViewModel extends AndroidViewModel {

    private HomePageRepository homePageRepository;

    public InfoViewModel(@NonNull Application application) {
        super(application);

        homePageRepository=new HomePageRepository();
    }

    public void setHomePageUrl (String urlString){
        homePageRepository.setHomePageUrl(urlString);
    }

    public LiveData<DataSnapshot> getStringUrl(){

        return homePageRepository.getDataUrl();
    }
}
