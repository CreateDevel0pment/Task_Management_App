package com.example.codeacademyapp.spalsh;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.users.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SplashRepository {

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> isUserAuthenticateInFirebaseMutableLiveData;
    private MutableLiveData<User> getChild;
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
        }, 2000);

        return isUserAuthenticateInFirebaseMutableLiveData;
    }

    MutableLiveData<User> getChil (){
       FirebaseDatabase database= FirebaseDatabase.getInstance();
       getChild=new MutableLiveData<>();

        final DatabaseReference userRef = database.getReference();

        Query query= userRef.child("Users").orderByChild("Name").equalTo("");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user=dataSnapshot.getValue(User.class);
                getChild.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return getChild;
    }

}