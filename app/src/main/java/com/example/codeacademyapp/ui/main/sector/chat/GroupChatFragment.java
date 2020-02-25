package com.example.codeacademyapp.ui.main.sector.chat;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.MessageGroupAdapter;
import com.example.codeacademyapp.data.model.MessageFromGroup;
import com.example.codeacademyapp.ui.main.sector.task.TaskActivity;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.example.codeacademyapp.ui.sign_in_up.fragments.UserInformationViewModel;
import com.example.codeacademyapp.utils.NetworkConnectivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupChatFragment extends BaseFragment {

    private ImageButton sentMessage_btn;
    private EditText userMessage_input;
    private String currentUserName, userImage;
    private String userGroup;
    private String userID;

    private ChatViewModel groupChatViewModel;
    private UserInformationViewModel userInformationViewModel;


    private RecyclerView chat_recycler;
    private MessageGroupAdapter adapter;

    private List<MessageFromGroup> messageList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        displayMessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_group_chat, container, false);

        FloatingActionButton new_task_btn = view.findViewById(R.id.new_task_button);
        new_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TaskActivity.class);
                startActivity(intent);
            }
        });

        initialisedFields(view);
        sentMessage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveMessageToDataBase();
                userMessage_input.setText("");
            }
        });

        return view;
    }

    private void SaveMessageToDataBase() {

        String message = userMessage_input.getText().toString();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), "Write your message", Toast.LENGTH_SHORT).show();
        } else {

            Calendar calForDate = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd MMM,yyyy");

            String currentDate = currentDateFormat.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm");

            String currentTime = currentTimeFormat.format(calForTime.getTime());

            groupChatViewModel.saveMessageFromGroupChat(userID, userGroup, currentUserName, userImage, message, currentDate, currentTime);

        }
    }

    private void displayMessage() {

        userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        userInformationViewModel.getUserInformation().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentUserName = Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString();

                    userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                    userID = dataSnapshot.getRef().getKey();

                    setTitle(userGroup + " Sector");

                    if (dataSnapshot.child("image").getValue() != null) {
                        userImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                    }

                    displayMessageToGroup(userGroup);
                }
            }
        });
    }

    private void displayMessageToGroup(String userGroup) {

        groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        groupChatViewModel.displayMessageToGroup(userGroup).observe(GroupChatFragment.this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                MessageFromGroup messages = dataSnapshot.getValue(MessageFromGroup.class);

                messageList.add(messages);
                adapter = new MessageGroupAdapter(messageList, getContext());
                chat_recycler.setAdapter(adapter);

                chat_recycler.smoothScrollToPosition(Objects.requireNonNull(chat_recycler.getAdapter()).getItemCount());
            }
        });
    }

    private void initialisedFields(View view) {
        sentMessage_btn = view.findViewById(R.id.sent_message_btn);
        userMessage_input = view.findViewById(R.id.input_user_message);

        chat_recycler = view.findViewById(R.id.group_chat_recycler);
        chat_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();


        if(NetworkConnectivity.isConnectivityNetworkAvailable(getContext())){

            Toast connectivityToast = Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG);
            connectivityToast.setGravity(Gravity.CENTER,0,0);
            connectivityToast.show();
        }
    }
}
