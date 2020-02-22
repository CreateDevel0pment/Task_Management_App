package com.example.codeacademyapp.ui.main.wall;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.codeacademyapp.adapters.MessageWallAdapter;
import com.example.codeacademyapp.data.model.MessagesFromWall;
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcademyWallFragment extends BaseFragment {

    private ImageButton sentMessage_btn;
    private EditText userMessage_input;

    private String currentUserName,id;
    private String userGroup, userImage;

    private View view;

    private ChatViewModel wallChatViewModel;

    private RecyclerView recyclerView;
    private MessageWallAdapter adapter;

    List<MessagesFromWall> messageList = new ArrayList<>();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        wallChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        wallChatViewModel.displayMessageToWall().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                MessagesFromWall messages = dataSnapshot.getValue(MessagesFromWall.class);

                messageList.add(messages);
                adapter = new MessageWallAdapter(messageList);
                recyclerView.setAdapter(adapter);

                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            return view;
        }

        view = inflater.inflate(R.layout.fragment_academy_wall, container, false);

        setTitle(R.string.academy_wall);
        initializedView(view);
        getUserInfo();

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

            wallChatViewModel.saveMessageFromWallChat(id,currentUserName, userGroup, userImage, message, currentDate, currentTime);

        }
    }

    private void getUserInfo() {

        wallChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                currentUserName = dataSnapshot.child("Name").getValue().toString();
                userGroup = dataSnapshot.child("Sector").getValue().toString();
                id=dataSnapshot.getRef().getKey();

                if(dataSnapshot.child("image").getValue()!= null){
                    userImage = dataSnapshot.child("image").getValue().toString();

                }

            }
        });
    }

    private void initializedView(View view) {
        sentMessage_btn = view.findViewById(R.id.sent_message_btn);
        userMessage_input = view.findViewById(R.id.input_user_message);
        recyclerView = view.findViewById(R.id.group_chat_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
