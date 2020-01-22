package com.example.codeacademyapp.users.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.users.model.User;
import com.example.codeacademyapp.users.repository.SigInRepository;

public class UserViewModel extends AndroidViewModel {

    private SigInRepository signInRepository;
    private LiveData<User> authenticatedUserLiveData;

    private SignUpRepository signUpRepository;
    private LiveData<User> signUpNewUserLiveData;


    public UserViewModel(@NonNull Application application) {
        super(application);

        signInRepository = new SigInRepository();
        signUpRepository = new SignUpRepository();
    }

    public void signUpNewUser(User user){
        signUpNewUserLiveData=signUpRepository.signUpNewUser(user);
    }

    public void forgotPassword(String email){
        signInRepository.forgotPassword(email);
    }

    public void signInNewUser(String mail, String password) {
        authenticatedUserLiveData = signInRepository.authUserInformation(mail, password);
    }

    public LiveData<User> getAuthenticatedUserLiveData() {
        return authenticatedUserLiveData;
    }

    public LiveData<User> getSignUpNewUserLiveData() {
        return signUpNewUserLiveData;
    }
}
