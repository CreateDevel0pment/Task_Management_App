package com.example.codeacademyapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.data.Resource;
import com.example.codeacademyapp.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Objects;

public class SigInRepository {

    private MutableLiveData<Resource<FirebaseUser>> userInformationsMutableLiveData;
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private DatabaseReference usersRef;
    private FirebaseUser firebaseUser;

    public MutableLiveData<Resource<FirebaseUser>> authUserInformation(String email, String password) {

        userInformationsMutableLiveData = new MutableLiveData<>();
        usersRef= FirebaseDatabase.getInstance().getReference().child("Users");

        if (!email.equals("") && !password.equals("")) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {

                        final boolean isNewUser = Objects.requireNonNull(Objects.requireNonNull(task.getResult())
                                .getAdditionalUserInfo()).isNewUser();
                        firebaseUser = firebaseAuth.getCurrentUser();

                        if (firebaseUser != null) {

                            final String uid = firebaseUser.getUid();
                            final String name = firebaseUser.getDisplayName();
                            final String email = firebaseUser.getEmail();

                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {

                                    if(task.isSuccessful()){

                                        final String deviceToken= Objects.requireNonNull(task.getResult()).getToken();

                                        usersRef.child(uid).child("Device_token").setValue(deviceToken)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful()){

                                                    User user = new User(uid, name, email,deviceToken);
                                                    user.isNew = isNewUser;

                                                    userInformationsMutableLiveData.setValue(Resource.success(firebaseUser));
                                                }

                                            }
                                        });

                                    }
                                }
                            });
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if(e instanceof FirebaseAuthInvalidCredentialsException){
                        userInformationsMutableLiveData.setValue(Resource.error("Invalid password",firebaseUser));

                    }else if(e instanceof FirebaseAuthInvalidUserException){
                        userInformationsMutableLiveData.setValue(Resource.error("Invalid email address",firebaseUser));

                    }else {
                        userInformationsMutableLiveData.setValue(Resource.error(e.getLocalizedMessage(),firebaseUser));
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
