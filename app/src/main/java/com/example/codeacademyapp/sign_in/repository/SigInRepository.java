package com.example.codeacademyapp.sign_in.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.codeacademyapp.utils.HeplerError.logErrorMessage;

public class SigInRepository {

    private MutableLiveData<User> userInformations;
    private FirebaseAuth firebaseAuth;

    public SigInRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        userInformations = new MutableLiveData<>();
    }

    public MutableLiveData<User> authUserInformation(String email, String password) {

        if (!email.equals("") && !password.equals("")) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String uid = firebaseUser.getUid();
                            String name = firebaseUser.getDisplayName();
                            String email = firebaseUser.getEmail();
                            User user = new User(uid, name, email);
                            user.isNew = isNewUser;
                            userInformations.setValue(user);
                        }

                    } else {
                        logErrorMessage("Please Sig Up!");
                    }
                }
            });
        }
        return userInformations;
    }

    public void forgotPassword(String email) {
        if (!email.equals("")) {
            firebaseAuth.sendPasswordResetEmail(email);
        }
    }
}
