package com.example.codeacademyapp.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.users.model.User;
import com.google.firebase.auth.FirebaseUser;

public class SplashViewModel extends AndroidViewModel {

    private SplashRepository splashRepository;

    public LiveData<FirebaseUser> isUserAuthenticatedLiveData;
    public LiveData<User> getChildData;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        splashRepository = new SplashRepository();
    }

    public void checkIfUserIsAuthenticated() {
        isUserAuthenticatedLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    public void getChildData(){
        getChildData=splashRepository.getChil();
    }

    public LiveData<FirebaseUser> getIsUserAuthenticatedLiveData() {
        return isUserAuthenticatedLiveData;
    }

    public LiveData<User> getGetChildData() {
        return getChildData;
    }
}
