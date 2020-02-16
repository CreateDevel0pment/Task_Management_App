package com.example.codeacademyapp.ui.sign_in_up.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.repository.GetUserInformationRepository;
import com.google.firebase.database.DataSnapshot;

public class UserInformationViewModel extends AndroidViewModel {

    GetUserInformationRepository userInformationRepository;

    public UserInformationViewModel(@NonNull Application application) {
        super(application);

        userInformationRepository=new GetUserInformationRepository();
    }

    public LiveData<DataSnapshot> getUserInformation (){

        return userInformationRepository.getUserInformation();
    }

    public String getUserId(){
        return userInformationRepository.getUserId();
    }
}
