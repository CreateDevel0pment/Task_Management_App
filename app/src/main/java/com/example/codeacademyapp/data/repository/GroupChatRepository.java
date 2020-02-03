package com.example.codeacademyapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class GroupChatRepository {

    private FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth auth;

    public void setGroupNameToFirebase(String groupName) {

        myRef = rootRef.getReference();
        myRef.child("Groups").child(groupName).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //TODO
                }
            }
        });
    }

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
}
