package com.example.codeacademyapp.data.repository;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

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

    public MutableLiveData<DataSnapshot> getChatRequest(final String currentUserId) {

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
        final MutableLiveData<DataSnapshot> displayMessage = new MutableLiveData<>();

        myRef = FirebaseDatabase.getInstance().getReference().child("Chat Sector").child(group_name);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    displayMessage.setValue(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    displayMessage.setValue(dataSnapshot);
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

        return displayMessage;
    }


    public MutableLiveData<DataSnapshot> displayMessageOnPublicWall() {
        final MutableLiveData<DataSnapshot> displayMessage = new MutableLiveData<>();

        myRef = FirebaseDatabase.getInstance().getReference().child("Chat Public Wall");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    displayMessage.setValue(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    displayMessage.setValue(dataSnapshot);
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

        return displayMessage;

    }

    public MutableLiveData<DataSnapshot> displayMessageOnPrivateChat(String message_reciever_id) {
        final MutableLiveData<DataSnapshot> displayMessage = new MutableLiveData<>();

        auth = FirebaseAuth.getInstance();
        String message_sender_id = auth.getCurrentUser().getUid();
        privateReference = FirebaseDatabase.getInstance().getReference();

        privateReference.child("Chat Private").child(message_sender_id).child(message_reciever_id)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        if (dataSnapshot.exists()) {
                            displayMessage.setValue(dataSnapshot);
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        if (dataSnapshot.exists()) {
                            displayMessage.setValue(dataSnapshot);
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


        return displayMessage;
    }

    public void saveMessageForPublicChat(String userId, String currentUserName, String userGroup, String image,
                                         String message, String currentDate,
                                         String currentTime) {

        myRef = FirebaseDatabase.getInstance().getReference().child("Chat Public Wall");
        String messageKey = myRef.push().getKey();

        HashMap<String, Object> groipMessageKey = new HashMap<>();
        myRef.updateChildren(groipMessageKey);

        if (messageKey != null) {
            groupMessageKeyRef = myRef.child(messageKey);
        }

        HashMap<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("id", userId);
        messageInfoMap.put("name", currentUserName);
        messageInfoMap.put("image", image);
        messageInfoMap.put("sector", userGroup);
        messageInfoMap.put("message", message);
        messageInfoMap.put("date", currentDate);
        messageInfoMap.put("time", currentTime);

        groupMessageKeyRef.updateChildren(messageInfoMap);
    }


    public void saveDocForPublicChat(final String userId, final String currentUserName, final String userGroup, final String image,
                                     final Uri docRef, final String currentDate,
                                     final String currentTime) {

        myRef = FirebaseDatabase.getInstance().getReference().child("Chat Public Wall");
        final String messageKey = myRef.push().getKey();

        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Send Picture");
        StorageReference storagePath= storageReference.child(userId + ".jpg");
        storagePath.putFile(docRef).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){

                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            HashMap<String, Object> groipMessageKey = new HashMap<>();
                            myRef.updateChildren(groipMessageKey);

                            if (messageKey != null) {
                                groupMessageKeyRef = myRef.child(messageKey);
                            }

                            HashMap<String, Object> messageInfoMap = new HashMap<>();
                            messageInfoMap.put("id", userId);
                            messageInfoMap.put("name", currentUserName);
                            messageInfoMap.put("image", image);
                            messageInfoMap.put("sector", userGroup);
                            messageInfoMap.put("message", uri.toString());
                            messageInfoMap.put("date", currentDate);
                            messageInfoMap.put("time", currentTime);

                            groupMessageKeyRef.updateChildren(messageInfoMap);

                        }
                    });
                }
            }
        });

    }

    public void saveMessageForGroupChat(String userId, String group_name, String currentUserName, String userImage,
                                        String message, String currentDate,
                                        String currentTime) {

        myRef = FirebaseDatabase.getInstance().getReference().child("Chat Sector").child(group_name);
        String messageKey = myRef.push().getKey();

        HashMap<String, Object> groipMessageKey = new HashMap<>();
        myRef.updateChildren(groipMessageKey);

        if (messageKey != null) {
            groupMessageKeyRef = myRef.child(messageKey);
        }

        HashMap<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("id", userId);
        messageInfoMap.put("sector", group_name);
        messageInfoMap.put("name", currentUserName);
        messageInfoMap.put("image", userImage);
        messageInfoMap.put("message", message);
        messageInfoMap.put("date", currentDate);
        messageInfoMap.put("time", currentTime);

        groupMessageKeyRef.updateChildren(messageInfoMap);
    }
}
