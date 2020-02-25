package com.example.codeacademyapp.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class ChatRepository {

    private FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
    private DatabaseReference myRef,
            groupMessageKeyRef,
            privateReference,
            chatRequestRef;

    private FirebaseAuth auth;

    public void setGroupNameToFirebase(String groupName) {

        myRef = rootRef.getReference();
        myRef.child("Groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //TODO
                }
            }
        });
    }


    public MutableLiveData<DataSnapshot> getchatRequest (final String currentUserId){

        final MutableLiveData<DataSnapshot> setChatRequest = new MutableLiveData<>();
        chatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");

        chatRequestRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    setChatRequest.setValue(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return setChatRequest;
    }

    public MutableLiveData<DataSnapshot> displayMessageInUsersGroup(String group_name) {
        final MutableLiveData<DataSnapshot> dispalyMessage = new MutableLiveData<>();

        myRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(group_name);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    dispalyMessage.setValue(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    dispalyMessage.setValue(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return dispalyMessage;
    }


    public MutableLiveData<DataSnapshot> displayMessageOnPublicWall() {
        final MutableLiveData<DataSnapshot> dispalyMessage = new MutableLiveData<>();

        myRef = FirebaseDatabase.getInstance().getReference().child("Wall").child("Public Chat");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    dispalyMessage.setValue(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    dispalyMessage.setValue(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return dispalyMessage;

    }

    public MutableLiveData<DataSnapshot> displayMessageOnPrivateChat(String message_reciever_id) {
        final MutableLiveData<DataSnapshot> dispalyMessage = new MutableLiveData<>();

        auth = FirebaseAuth.getInstance();
        String message_sender_id=auth.getCurrentUser().getUid();
        privateReference=FirebaseDatabase.getInstance().getReference();

        privateReference.child("Message").child(message_sender_id).child(message_reciever_id)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        if (dataSnapshot.exists()) {
                            dispalyMessage.setValue(dataSnapshot);
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        if (dataSnapshot.exists()) {
                            dispalyMessage.setValue(dataSnapshot);
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        return dispalyMessage;
    }

    public void saveMessageForPublicChat(String userId,String currentUserName, String userGroup, String image,
                                         String message, String currentDate,
                                         String currentTime) {


        myRef = FirebaseDatabase.getInstance().getReference().child("Wall").child("Public Chat");
        String messageKey = myRef.push().getKey();

        HashMap<String, Object> groipMessageKey = new HashMap<>();
        myRef.updateChildren(groipMessageKey);

        if (messageKey != null) {
            groupMessageKeyRef = myRef.child(messageKey);
        }

        HashMap<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("id",userId);
        messageInfoMap.put("name", currentUserName);
        messageInfoMap.put("image", image);
        messageInfoMap.put("sector", userGroup);
        messageInfoMap.put("message", message);
        messageInfoMap.put("date", currentDate);
        messageInfoMap.put("time", currentTime);

        groupMessageKeyRef.updateChildren(messageInfoMap);
    }

    public void saveMessageForGroupChat(String userId,String group_name, String currentUserName,String userImage,
                                        String message, String currentDate,
                                        String currentTime) {

        myRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(group_name);
        String messageKey = myRef.push().getKey();

        HashMap<String, Object> groipMessageKey = new HashMap<>();
        myRef.updateChildren(groipMessageKey);

        if (messageKey != null) {
            groupMessageKeyRef = myRef.child(messageKey);
        }

        HashMap<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("id",userId);
        messageInfoMap.put("sector",group_name);
        messageInfoMap.put("name", currentUserName);
        messageInfoMap.put("image",userImage);
        messageInfoMap.put("message", message);
        messageInfoMap.put("date", currentDate);
        messageInfoMap.put("time", currentTime);

        groupMessageKeyRef.updateChildren(messageInfoMap);
    }
}
