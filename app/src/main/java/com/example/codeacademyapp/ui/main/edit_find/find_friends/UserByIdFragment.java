package com.example.codeacademyapp.ui.main.edit_find.find_friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.codeacademyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserByIdFragment extends Fragment {

    private String receiver_user_id, sender_user_id, current_State;

    private CircleImageView userProfileImage;
    private TextView userProfileName, userProfileGroup;
    private Button send_message_btn;

    private DatabaseReference userRef, chatRequestRef, contactRef;
    private FirebaseAuth auth;

    public UserByIdFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_by_id, container, false);
        initializedView(view);

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        chatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        contactRef = FirebaseDatabase.getInstance().getReference().child("Contacts");

        auth = FirebaseAuth.getInstance();
        sender_user_id = auth.getCurrentUser().getUid();


        if (getArguments() != null) {
            receiver_user_id = getArguments().getString("user_by_id");
        }

        current_State="new";
        retreiveUserInformations();

        return view;
    }

    private void retreiveUserInformations() {

        userRef.child(receiver_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && (dataSnapshot.hasChild("image"))) {
                    String userImage = dataSnapshot.child("image").getValue().toString();
                    String userName = dataSnapshot.child("Name").getValue().toString();
                    String userGroup = dataSnapshot.child("Sector").getValue().toString();

                    Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(userProfileImage);
                    userProfileName.setText(userName);
                    userProfileGroup.setText(userGroup);

                    menageChatRequest();

                } else {

                    String userName = dataSnapshot.child("Name").getValue().toString();
                    String userGroup = dataSnapshot.child("Sector").getValue().toString();
                    userProfileName.setText(userName);
                    userProfileGroup.setText(userGroup);

                    menageChatRequest();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void menageChatRequest() {

        chatRequestRef.child(sender_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(receiver_user_id)) {

                    String request_type = dataSnapshot.child(receiver_user_id).child("request_type").getValue().toString();

                    if (request_type.equals("send")) {
                        current_State = "request send";
                        send_message_btn.setText("Cancel Chat Request");

                    } else if (request_type.equals("received")) {
                        current_State = "request_received";
                        send_message_btn.setText("Accept Chat Request");

                    }
                }else {

                    contactRef.child(sender_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild(receiver_user_id)){
                                current_State = "friends";
                                send_message_btn.setText("remove this contact");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (!sender_user_id.equals(receiver_user_id)) {

            send_message_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    send_message_btn.setEnabled(false);

                    if (current_State.equals("new")) {

                        sendChatRequest();
                    }
                    if (current_State.equals("request send")) {

                        cancelChatRequest();
                    }
                    if (current_State.equals("request_received")) {

                        acceptChatRequest();
                    }if(current_State.equals("friends")){

                        removeSpecificContact();
                    }

                }
            });


        } else {

            send_message_btn.setVisibility(View.INVISIBLE);
        }


    }

    private void removeSpecificContact() {

        contactRef.child(sender_user_id).child(receiver_user_id)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            send_message_btn.setText("Send Message");

                            contactRef.child(receiver_user_id).child(sender_user_id)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                send_message_btn.setEnabled(true);
                                                current_State = "new";
                                                send_message_btn.setText("Send Message");

                                            }

                                        }
                                    });
                        }
                    }
                });

    }

    private void acceptChatRequest() {

        contactRef.child(sender_user_id).child(receiver_user_id).child("Contacts").setValue("Saved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            contactRef.child(receiver_user_id).child(sender_user_id).child("Contacts").setValue("Saved")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){

                                                chatRequestRef.child(sender_user_id).child(receiver_user_id)
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if(task.isSuccessful()){

                                                                    chatRequestRef.child(receiver_user_id).child(sender_user_id)
                                                                            .removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                                    send_message_btn.setEnabled(true);
                                                                                    current_State = "Friends";
                                                                                    send_message_btn.setText("Remove this Contact");
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    private void cancelChatRequest() {

        chatRequestRef.child(sender_user_id).child(receiver_user_id)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            send_message_btn.setText("Send Message");

                            chatRequestRef.child(receiver_user_id).child(sender_user_id)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                send_message_btn.setEnabled(true);
                                                current_State = "new";
                                                send_message_btn.setText("Send Message");

                                            }

                                        }
                                    });
                        }
                    }
                });
    }

    private void sendChatRequest() {

        chatRequestRef.child(sender_user_id).child(receiver_user_id)
                .child("request_type").setValue("send").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                chatRequestRef.child(receiver_user_id).child(sender_user_id)
                        .child("request_type").setValue("received")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    send_message_btn.setEnabled(true);
                                    current_State = "request send";
                                    send_message_btn.setText("Cancel Chat Request");
                                }

                            }
                        });

            }
        });

    }

    private void initializedView(View view) {

        userProfileImage = view.findViewById(R.id.visit_profile_image);
        userProfileName = view.findViewById(R.id.visit_user_name);
        userProfileGroup = view.findViewById(R.id.visit_user_group);
        send_message_btn = view.findViewById(R.id.send_message_request_btn);
        current_State = "new";
    }

}
