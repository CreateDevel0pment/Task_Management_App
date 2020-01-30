package com.example.codeacademyapp.sign_in.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigInRepository {

    private MutableLiveData<FirebaseUser> userInformationsMutableLiveData;
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

    public MutableLiveData<FirebaseUser> authUserInformation(String email, String password) {
        userInformationsMutableLiveData = new MutableLiveData<>();

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
                            userInformationsMutableLiveData.setValue(firebaseUser);
                        }

                    } else {

                    }
                }
            });
        }
        return userInformationsMutableLiveData;
    }

    public void forgotPassword(String email) {
        if (!email.equals("")) {
            firebaseAuth.sendPasswordResetEmail(email);
        }
    }
}
