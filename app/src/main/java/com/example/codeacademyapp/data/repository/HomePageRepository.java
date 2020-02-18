package com.example.codeacademyapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePageRepository {

    private FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = rootRef.getReference();

    public void setHomePageUrl(String urlString) {

        myRef.child("HomePage").child("url").setValue(urlString);
    }

    public MutableLiveData<DataSnapshot> getDataUrl(){

        final MutableLiveData<DataSnapshot> getUrlString = new MutableLiveData<>();

        myRef.child("HomePage").child("url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                getUrlString.setValue(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return getUrlString;
    }
}
