package com.example.codeacademyapp.ui.sign_in_up;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.Resource;
import com.example.codeacademyapp.data.model.User;
import com.example.codeacademyapp.data.repository.SigInRepository;
import com.example.codeacademyapp.data.repository.SignUpRepository;
import com.google.firebase.auth.FirebaseUser;

public class LogUserViewModel extends AndroidViewModel {

    private SigInRepository signInRepository;
    private SignUpRepository signUpRepository;

    public LogUserViewModel(@NonNull Application application) {
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

    public LiveData<Resource<FirebaseUser>> signInNewUser(String mail, String password) {
        return signInRepository.authUserInformation(mail, password);
    }

}

