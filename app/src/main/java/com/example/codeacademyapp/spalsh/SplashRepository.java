package com.example.codeacademyapp.spalsh;

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

    MutableLiveData<FirebaseUser> checkIfUserIsAuthenticatedInFirebase() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isUserAuthenticateInFirebaseMutableLiveData.setValue(firebaseUser);
            }
        }, 1500);

        return isUserAuthenticateInFirebaseMutableLiveData;
    }


}