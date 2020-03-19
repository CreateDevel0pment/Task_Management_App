package com.example.codeacademyapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ChatRequestRepository {

    private DatabaseReference chatRequestRef, notificationRef,contactRef;


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


    public MutableLiveData<Task> sendChatRequest(final String current_user_id, final String receiver_user_id){

        final MutableLiveData<Task> getTaskRequest= new MutableLiveData<>();

        chatRequestRef=FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        notificationRef =FirebaseDatabase.getInstance().getReference().child("Notifications");

        chatRequestRef.child(current_user_id).child(receiver_user_id)
                .child("request_type")
                .setValue("send").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                chatRequestRef.child(receiver_user_id).child(current_user_id)
                        .child("request_type").setValue("received")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    HashMap<String, String> chatNotificationMap = new HashMap<>();
                                    chatNotificationMap.put("from", current_user_id);
                                    chatNotificationMap.put("type", "request");

                                    notificationRef.child(receiver_user_id).push()
                                            .setValue(chatNotificationMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {

                                                        getTaskRequest.setValue(task);

                                                    }
                                                }
                                            });

                                }
                            }
                        });

            }
        });

        return getTaskRequest;

    }


    public MutableLiveData<Task> cancelChatRequest(final String current_user_id, final String receiver_user_id){

        final MutableLiveData<Task> getTaskRequest= new MutableLiveData<>();

        chatRequestRef=FirebaseDatabase.getInstance().getReference().child("Chat Requests");

        chatRequestRef.child(current_user_id).child(receiver_user_id)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            chatRequestRef.child(receiver_user_id).child(current_user_id)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                               getTaskRequest.setValue(task);

                                            }

                                        }
                                    });
                        }
                    }
                });

        return getTaskRequest;

    }

    public MutableLiveData<Task> acceptChatRequest(final String current_user_id, final String receiver_user_id){
        final MutableLiveData<Task> getTaskRequest= new MutableLiveData<>();

        contactRef = FirebaseDatabase.getInstance().getReference().child("Contacts");


        contactRef.child(current_user_id).child(receiver_user_id).child("Contacts").setValue("Saved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            contactRef.child(receiver_user_id).child(current_user_id).child("Contacts").setValue("Saved")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){

                                                getTaskRequest.setValue(task);

                                            }
                                        }
                                    });

                        }



                    }
                });


        return getTaskRequest;
    }

    public MutableLiveData<Task> removeFromMyContacts (final String current_user_id, final String receiver_user_id){
        final MutableLiveData<Task> getTaskRequest= new MutableLiveData<>();

        contactRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        contactRef.child(current_user_id).child(receiver_user_id)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    contactRef.child(receiver_user_id).child(current_user_id)
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            getTaskRequest.setValue(task);
                        }
                    });
                }

            }
        });

        return getTaskRequest;
    }


    public MutableLiveData<DataSnapshot> removeContacts (final String current_user_id, final String receiver_user_id){
        final MutableLiveData<DataSnapshot> getTaskRequest= new MutableLiveData<>();

        contactRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        contactRef.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(receiver_user_id)){

                    getTaskRequest.setValue(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return getTaskRequest;
    }
}
