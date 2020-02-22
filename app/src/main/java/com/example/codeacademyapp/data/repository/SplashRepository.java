package com.example.codeacademyapp.data.repository;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashRepository {

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> isUserAuthenticateInFirebaseMutableLiveData;
    private FirebaseUser firebaseUser;

    public SplashRepository() {
        isUserAuthenticateInFirebaseMutableLiveData = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    public MutableLiveData<FirebaseUser> checkIfUserIsAuthenticatedInFirebase() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                isUserAuthenticateInFirebaseMutableLiveData.setValue(firebaseUser);
            }
        }, 1000);

        return isUserAuthenticateInFirebaseMutableLiveData;
    }

}