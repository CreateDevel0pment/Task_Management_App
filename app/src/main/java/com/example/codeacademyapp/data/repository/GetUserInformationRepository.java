package com.example.codeacademyapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class GetUserInformationRepository {

    private FirebaseAuth auth;
    private DatabaseReference myRef;
    private String currentUserId;


    public MutableLiveData<DataSnapshot> getUserInformation() {
        final MutableLiveData<DataSnapshot> getUserInformations = new MutableLiveData<>();

        auth = FirebaseAuth.getInstance();
        currentUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users");

        myRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    getUserInformations.setValue(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return getUserInformations;
    }

    public String getUserId() {

        auth=FirebaseAuth.getInstance();
        String userId=auth.getCurrentUser().getUid();

        return userId;
    }


    public MutableLiveData<DataSnapshot> retrieveRecieverUserInfo (String receiver_user_id){
        final MutableLiveData<DataSnapshot> setUserInfo = new MutableLiveData<>();

        myRef = FirebaseDatabase.getInstance().getReference().child("Users");


        myRef.child(receiver_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                setUserInfo.setValue(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return setUserInfo;
    }


    public MutableLiveData<DataSnapshot> myContactsInfo (){
        final MutableLiveData<DataSnapshot> contactInfo = new MutableLiveData<>();

        myRef = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        myRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                contactInfo.setValue(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return contactInfo;
    }
}
