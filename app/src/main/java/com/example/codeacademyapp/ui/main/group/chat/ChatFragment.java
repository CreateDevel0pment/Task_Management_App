package com.example.codeacademyapp.ui.main.group.chat;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.group.task.NewTaskActivity;
import com.example.codeacademyapp.ui.main.group.task.fragment.AddNewTaskFragment;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment {

    private ImageButton sentMessage_btn;
    private EditText userMessage_input;
    private ScrollView scrollView;
    private TextView display_text_messages;

    private String currentUserName;
    private String userGroup;
    private String messageKey;

    private ChatViewModel groupChatViewModel;

//    private FirebaseAuth auth;
//    private String currentUserId;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        groupChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                    messageKey = dataSnapshot.child("Sector").getKey();
                    setTitle(userGroup + " Sector");

                }

                groupChatViewModel.displayMessageToGroup(userGroup).observe(ChatFragment.this, new Observer<DataSnapshot>() {
                    @Override
                    public void onChanged(DataSnapshot dataSnapshot) {

                        displayMessages(dataSnapshot);
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_group_chat, container, false);

        Button new_task_btn = view.findViewById(R.id.new_task_button);
        new_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });
        Button add_task_btn = view.findViewById(R.id.add_task_button);
        add_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddNewTaskFragment addNewTaskFragment = new AddNewTaskFragment();
                FragmentManager manager = getFragmentManager();
                if (manager != null) {
                    manager.beginTransaction().replace(R.id.group_container, addNewTaskFragment).commit();
                }
            }
        });

//        auth = FirebaseAuth.getInstance();
//        currentUserId = auth.getCurrentUser().getUid();
//        userRefereces = FirebaseDatabase.getInstance().getReference().child("Users");

        initialisedFields(view);
        getUserInfo();

        sentMessage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveMessageToDataBase();

                userMessage_input.setText("");
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
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

            groupChatViewModel.saveMessageFromGroupChat(userGroup, currentUserName, message, currentDate, currentTime);

        }
    }

    private void getUserInfo() {

        groupChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    currentUserName = dataSnapshot.child("Name").getValue().toString();
                }
            }
        });

    }

    private void initialisedFields(View view) {
        sentMessage_btn = view.findViewById(R.id.sent_message_btn);
        userMessage_input = view.findViewById(R.id.input_user_message);
        display_text_messages = view.findViewById(R.id.group_chat_display);
        scrollView = view.findViewById(R.id.scroll_view);
    }

    private void displayMessages(DataSnapshot dataSnapshot) {

        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()) {

            String chatDate = (String) iterator.next().getValue();
            String chatMessage = (String) iterator.next().getValue();
            String chatName = (String) iterator.next().getValue();
            String chatTime = (String) iterator.next().getValue();

            display_text_messages.append(chatName + ":" + " \n"
                    + "message: " + chatMessage + " \n"
                    + chatTime
                    + "           " + chatDate + "\n\n\n");

            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }
}
