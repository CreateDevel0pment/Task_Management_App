package com.example.codeacademyapp.spalsh;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

public class SplashViewModel extends AndroidViewModel {

    private SplashRepository splashRepository;

    private LiveData<FirebaseUser> isUserAuthenticatedLiveData;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        splashRepository = new SplashRepository();
    }

    public void checkIfUserIsAuthenticated() {
        isUserAuthenticatedLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }


    public LiveData<FirebaseUser> getIsUserAuthenticatedLiveData() {
        return isUserAuthenticatedLiveData;
    }

}
