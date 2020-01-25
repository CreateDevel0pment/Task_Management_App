package com.example.codeacademyapp.sign_in;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.model.User;
import com.example.codeacademyapp.sign_in.repository.SigInRepository;
import com.example.codeacademyapp.sign_in.repository.SignUpRepository;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends AndroidViewModel {

    private SigInRepository signInRepository;
    private SignUpRepository signUpRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);

        signInRepository = new SigInRepository();
        signUpRepository = new SignUpRepository();
    }

    public LiveData<User> signUpNewUser(User user) {
        return signUpRepository.signUpNewUser(user);
    }

    public void forgotPassword(String email) {
        signInRepository.forgotPassword(email);
    }

    public LiveData<FirebaseUser> signInNewUser(String mail, String password) {
        return signInRepository.authUserInformation(mail, password);
    }

}

