package com.example.codeacademyapp.ui.sign_in_up.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.repository.GetUserInformationRepository;
import com.google.firebase.database.DataSnapshot;

public class UserInformationViewModel extends AndroidViewModel {

    private GetUserInformationRepository userInformationRepository;

    public UserInformationViewModel(@NonNull Application application) {
        super(application);

        userInformationRepository=new GetUserInformationRepository();
    }

    public LiveData<DataSnapshot> getUserInformation (){

        return userInformationRepository.getUserInformation();
    }

    public LiveData<Integer> getUserTasksStats (String userId){
        return userInformationRepository.getUserTasksStats(userId);
    }
    public LiveData<Integer> getUserCompletedTasksStats (String userId){
        return userInformationRepository.getUserCompletedTasksStats(userId);
    }

    public LiveData<DataSnapshot> retrieveRecieverUserInfo (String receiver_user_id){
        return userInformationRepository.retrieveRecieverUserInfo(receiver_user_id);
    }

    public LiveData<DataSnapshot> contactInfo (){
        return userInformationRepository.myContactsInfo();
    }

    public String getUserId(){
        return userInformationRepository.getUserId();
    }
}
