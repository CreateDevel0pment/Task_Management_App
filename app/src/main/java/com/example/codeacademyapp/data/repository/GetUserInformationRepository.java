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

    FirebaseAuth auth;
    DatabaseReference myRef;



    public MutableLiveData<DataSnapshot> getUserInformation() {
        final MutableLiveData<DataSnapshot> getUserInformations = new MutableLiveData<>();

        auth = FirebaseAuth.getInstance();
        String currentUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
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
}
