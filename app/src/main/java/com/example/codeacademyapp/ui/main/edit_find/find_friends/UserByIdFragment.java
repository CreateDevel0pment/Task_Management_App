package com.example.codeacademyapp.ui.main.edit_find.find_friends;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.example.codeacademyapp.ui.sign_in_up.fragments.UserInformationViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.codeacademyapp.utils.Constants.USER_BY_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserByIdFragment extends Fragment {

    private String receiver_user_id,
            current_user_id,
            current_State;

    private CircleImageView userProfileImage;
    private TextView userProfileName, userProfileGroup;
    private Button send_message_btn;

    private UserInformationViewModel userInformationViewModel;
    private ChatViewModel chatViewModel;
    private ChatRequestViewModel chatRequestViewModel;

    public UserByIdFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_by_id, container, false);
        initializedView(view);

        userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        current_user_id = userInformationViewModel.getUserId();

        if (getArguments() != null) {
            receiver_user_id = getArguments().getString(USER_BY_ID);
        }

        current_State = "new";
        retrieveUserInformation();

        return view;
    }

    private void retrieveUserInformation() {

        userInformationViewModel.retrieveRecieverUserInfo(receiver_user_id).observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && (dataSnapshot.hasChild("image"))) {
                    String userImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                    String userName = Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString();
                    String userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();

                    Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(userProfileImage);
                    userProfileName.setText(userName);
                    userProfileGroup.setText(userGroup);

                    menageChatRequest();

                } else {

                    String userName = Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString();
                    String userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                    userProfileName.setText(userName);
                    userProfileGroup.setText(userGroup);

                    menageChatRequest();

                }

            }
        });
    }

    private void menageChatRequest() {

        chatRequestViewModel = ViewModelProviders.of(this).get(ChatRequestViewModel.class);
        chatRequestViewModel.getChatRequestData(current_user_id).observe(this, new Observer<DataSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(receiver_user_id)) {

                    String request_type = Objects.requireNonNull(dataSnapshot.child(receiver_user_id)
                            .child("request_type").getValue()).toString();

                    if (request_type.equals("send")) {
                        current_State = "request send";
                        send_message_btn.setText("Cancel Chat Request");

                    } else if (request_type.equals("received")) {
                        current_State = "request_received";
                        send_message_btn.setText("Accept Chat Request");

                    }
                } else {
                    chatRequestViewModel.setValueForRemove(current_user_id, receiver_user_id)
                            .observe(UserByIdFragment.this, new Observer<DataSnapshot>() {
                                @Override
                                public void onChanged(DataSnapshot dataSnapshot) {

                                    current_State = "friends";
                                    send_message_btn.setText("remove this contact");
                                }
                            });
                }
            }
        });

        if (!current_user_id.equals(receiver_user_id)) {

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
                    }
                    if (current_State.equals("friends")) {

                        removeSpecificContact();
                    }
                }
            });
        } else {

            send_message_btn.setVisibility(View.INVISIBLE);
        }
    }

    private void removeSpecificContact() {

        chatRequestViewModel = ViewModelProviders.of(this).get(ChatRequestViewModel.class);
        chatRequestViewModel.removeFromMyContacts(current_user_id, receiver_user_id)
                .observe(this, new Observer<Task>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onChanged(Task task) {

                        if (task.isSuccessful()) {

                            send_message_btn.setEnabled(true);
                            current_State = "new";
                            send_message_btn.setText("Send Message");
                        }
                    }
                });

    }

    private void acceptChatRequest() {

        chatRequestViewModel = ViewModelProviders.of(this).get(ChatRequestViewModel.class);
        chatRequestViewModel.acceptChatRequest(current_user_id, receiver_user_id).observe(this, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {

                if ((task.isSuccessful())) {

                    chatRequestViewModel.cancelChatRequest(current_user_id, receiver_user_id)
                            .observe(UserByIdFragment.this, new Observer<Task>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onChanged(Task task) {

                                    send_message_btn.setEnabled(true);
                                    current_State = "friends";
                                    send_message_btn.setText("Remove this Contact");
                                }
                            });
                }
            }
        });

    }

    private void cancelChatRequest() {

        chatRequestViewModel = ViewModelProviders.of(this).get(ChatRequestViewModel.class);
        chatRequestViewModel.cancelChatRequest(current_user_id, receiver_user_id).observe(this, new Observer<Task>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Task task) {

                if (task.isSuccessful()) {

                    send_message_btn.setEnabled(true);
                    current_State = "new";
                    send_message_btn.setText("Send Message");
                }
            }
        });
    }

    private void sendChatRequest() {

        chatRequestViewModel = ViewModelProviders.of(this).get(ChatRequestViewModel.class);
        chatRequestViewModel.getChatRequest(current_user_id, receiver_user_id).observe(this, new Observer<Task>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Task task) {

                if (task.isSuccessful()) {

                    send_message_btn.setEnabled(true);
                    current_State = "request send";
                    send_message_btn.setText("Cancel Chat Request");
                }
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
