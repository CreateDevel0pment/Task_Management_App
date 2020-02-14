package com.example.codeacademyapp.ui.main.wall;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.MessageAdapter;
import com.example.codeacademyapp.adapters.MessageWallAdapter;
import com.example.codeacademyapp.data.model.Messages;
import com.example.codeacademyapp.data.model.MessagesFromWall;
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcademyWallFragment extends BaseFragment {

    private ImageButton sentMessage_btn;
    private EditText userMessage_input;

    private String currentUserName;
    private String userGroup,userImage;

    private View view;

    private ChatViewModel wallChatViewModel;

    private RecyclerView recyclerView;
    private MessageWallAdapter adapter;

    DatabaseReference roothRef;

    List<MessagesFromWall> messageList=new ArrayList<>();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        wallChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        wallChatViewModel.displayMessageToWall().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                MessagesFromWall messages=dataSnapshot.getValue(MessagesFromWall.class);

                messageList.add(messages);
                adapter=new MessageWallAdapter(messageList);
                recyclerView.setAdapter(adapter);
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

        recyclerView=view.findViewById(R.id.group_chat_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        roothRef = FirebaseDatabase.getInstance().getReference();

        setTitle(R.string.academy_wall);
        initialisedFields(view);

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

            wallChatViewModel.saveMessageFromWallChat(currentUserName, userGroup,userImage, message, currentDate, currentTime);

        }
    }

    private void getUserInfo() {

        wallChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                currentUserName = dataSnapshot.child("Name").getValue().toString();
                userGroup = dataSnapshot.child("Sector").getValue().toString();
                userImage=dataSnapshot.child("image").getValue().toString();
            }
        });
    }

    private void initialisedFields(View view) {
        sentMessage_btn = view.findViewById(R.id.sent_message_btn);
        userMessage_input = view.findViewById(R.id.input_user_message);
    }
}
