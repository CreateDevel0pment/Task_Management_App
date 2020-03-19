package com.example.codeacademyapp.data.repository;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.data.model.PublicMessage;
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

    public void saveMessageForPublicChat(PublicMessage message) {

        myRef = FirebaseDatabase.getInstance().getReference().child("Chat Public Wall");
        String messageKey = myRef.push().getKey();

        HashMap<String, Object> groipMessageKey = new HashMap<>();
        myRef.updateChildren(groipMessageKey);

        if (messageKey != null) {
            groupMessageKeyRef = myRef.child(messageKey);
        }

        HashMap<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("id", message.getId());
        messageInfoMap.put("name", message.getName());
        messageInfoMap.put("image", message.getImage());
        messageInfoMap.put("sector", message.getSector());
        messageInfoMap.put("message", message.getMessage());
        messageInfoMap.put("date", message.getDate());
        messageInfoMap.put("time", message.getTime());
        messageInfoMap.put("docType",message.getDocType());

        groupMessageKeyRef.updateChildren(messageInfoMap);
    }


    public void saveDocForPublicChat(final PublicMessage message) {

        myRef = FirebaseDatabase.getInstance().getReference().child("Chat Public Wall");
        final String messageKey = myRef.push().getKey();

        if (messageKey!=null) {

            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference()
                    .child("Send Doc Files");

            StorageReference storagePath = storageReference.child(message.getUri().toString());

            storagePath.putFile(message.getUri()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful()) {

                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                HashMap<String, Object> groipMessageKey = new HashMap<>();
                                myRef.updateChildren(groipMessageKey);

                                if (messageKey != null) {
                                    groupMessageKeyRef = myRef.child(messageKey);
                                }

                                HashMap<String, Object> messageInfoMap = new HashMap<>();
                                messageInfoMap.put("id", message.getId());
                                messageInfoMap.put("name", message.getName());
                                messageInfoMap.put("image", message.getImage());
                                messageInfoMap.put("sector", message.getSector());
                                messageInfoMap.put("message", uri.toString());
                                messageInfoMap.put("date", message.getDate());
                                messageInfoMap.put("time", message.getTime());
                                messageInfoMap.put("docType",message.getDocType());
                                messageInfoMap.put("docName",message.getDocName());

                                groupMessageKeyRef.updateChildren(messageInfoMap);

                            }
                        });
                    }
                }
            });

        }

    }

    public void saveMessageForGroupChat(PublicMessage message) {

        myRef = FirebaseDatabase.getInstance().getReference().child("Chat Sector").child(message.getSector());
        String messageKey = myRef.push().getKey();

        HashMap<String, Object> groipMessageKey = new HashMap<>();
        myRef.updateChildren(groipMessageKey);

        if (messageKey != null) {
            groupMessageKeyRef = myRef.child(messageKey);
        }

        HashMap<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("id", message.getId());
        messageInfoMap.put("sector", message.getSector());
        messageInfoMap.put("name", message.getName());
        messageInfoMap.put("image", message.getImage());
        messageInfoMap.put("message", message.getMessage());
        messageInfoMap.put("date", message.getDate());
        messageInfoMap.put("time", message.getTime());
        messageInfoMap.put("docType",message.getDocType());
        messageInfoMap.put("docName",message.getDocName());

        groupMessageKeyRef.updateChildren(messageInfoMap);
    }


    public void saveDocForGroupChat(final PublicMessage message) {

        myRef = FirebaseDatabase.getInstance().getReference().child("Chat Sector").child(message.getSector());
        final String messageKey = myRef.push().getKey();

        if (messageKey!=null) {

            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference()
                    .child("Send Doc Files");

            StorageReference storagePath = storageReference.child(message.getUri().toString());

            storagePath.putFile(message.getUri()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful()) {

                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                HashMap<String, Object> groipMessageKey = new HashMap<>();
                                myRef.updateChildren(groipMessageKey);

                                if (messageKey != null) {
                                    groupMessageKeyRef = myRef.child(messageKey);
                                }

                                HashMap<String, Object> messageInfoMap = new HashMap<>();
                                messageInfoMap.put("id", message.getId());
                                messageInfoMap.put("name", message.getName());
                                messageInfoMap.put("image", message.getImage());
                                messageInfoMap.put("sector", message.getSector());
                                messageInfoMap.put("message", uri.toString());
                                messageInfoMap.put("date", message.getDate());
                                messageInfoMap.put("time", message.getTime());
                                messageInfoMap.put("docType",message.getDocType());
                                messageInfoMap.put("docName",message.getDocName());

                                groupMessageKeyRef.updateChildren(messageInfoMap);

                            }
                        });
                    }
                }
            });

        }
    }
}
