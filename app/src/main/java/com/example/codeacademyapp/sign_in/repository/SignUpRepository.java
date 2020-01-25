package com.example.codeacademyapp.sign_in.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpRepository {

    private FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef = rootRef.getReference();

    public MutableLiveData<User> signUpNewUser(final User user) {
        final MutableLiveData<User> setUserInformation = new MutableLiveData<>();

        if (user != null) {
            mAuth.createUserWithEmailAndPassword(user.geteMail(), user.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();

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

                                User setUser =new User();
                                setUser.isNew=isNewUser;
                                setUser.setName(user.getName());
                                setUser.setSurname(user.getSurname());
                                setUser.setRole_spinner(user.getRole_spinner());
                                setUser.setGroup_spinner(user.getGroup_spinner());
                                setUserInformation.setValue(setUser);
                            }
                        }
                    }
                }
            });

        }
        return setUserInformation;
    }
}
