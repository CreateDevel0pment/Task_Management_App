package com.example.codeacademyapp.main.repository;

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

import java.util.Iterator;

public class GroupChatRepository {

    private FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth auth;

    private MutableLiveData<Iterator<DataSnapshot>> getGroupNamesFromFirebase;

    public void setGroupNameToFirebase(String groupName){

        myRef = rootRef.getReference();
        myRef.child("Groups").child(groupName).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //TODO
                }
            }
        });
    }

    public MutableLiveData<Iterator<DataSnapshot>> getGroupNamesFromFirebase(){
        getGroupNamesFromFirebase=new MutableLiveData<>();

        myRef = FirebaseDatabase.getInstance().getReference().child("Groups");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                getGroupNamesFromFirebase.setValue(dataSnapshot.getChildren().iterator());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return getGroupNamesFromFirebase;
    }

    public MutableLiveData<String> getReferencesForUsersGroup(){
        final MutableLiveData<String> getGroupName = new MutableLiveData<>();

        auth = FirebaseAuth.getInstance();
        final String currentUserId = auth.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users");

        myRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    getGroupName.setValue(dataSnapshot.child("Group").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return getGroupName;
    }
}