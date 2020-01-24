package com.example.codeacademyapp.sign_in.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpRepository {

    private MutableLiveData<User> setUserInformation;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    public SignUpRepository() {
        setUserInformation = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
    }

    public MutableLiveData<User> signUpNewUser(User user) {

        if (user != null) {
            mAuth.createUserWithEmailAndPassword(user.geteMail(), user.getPassword());

            if (!user.getName().equals("") && !user.getSurname().equals("") &&
                    !user.getRole_spinner().equals("") && !user.getGroup_spinner().equals("")) {

                FirebaseUser userFb = mAuth.getCurrentUser();
                String userID ;
                if (userFb != null) {
                    userID = userFb.getUid();

                    myRef.child("Users").child(userID)
                            .child("Name")
                            .setValue(user.getName());
                    myRef.child("Users").child(userID)
                            .child("Surname")
                            .setValue(user.getSurname());
                    myRef.child("Users").child(userID)
                            .child("Group")
                            .setValue(user.getGroup_spinner());
                    myRef.child("Users").child(userID)
                            .child("Role")
                            .setValue(user.getRole_spinner());
                }
            }
        }
        return setUserInformation;
    }
}