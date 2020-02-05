package com.example.codeacademyapp.ui.main.wall;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.NewTaskPagerAdapter;
import com.example.codeacademyapp.adapters.WallPagerAdapter;
import com.example.codeacademyapp.ui.main.group.chat.ChatViewModel;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcademyWallFragment extends BaseFragment {

    private ImageButton sentMessage_btn;
    private EditText userMessage_input;
    private ScrollView scrollView;
    private TextView display_text_messages;

//    private String currentUserId;
    private String currentUserName;
    private String userGroup;

//    private FirebaseAuth auth;

    ChatViewModel wallChatViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        wallChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        wallChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    wallChatViewModel.displayMessageToWall().observe(AcademyWallFragment.this, new Observer<DataSnapshot>() {
                        @Override
                        public void onChanged(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {

                                displayMessages(dataSnapshot);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_academy_wall, container, false);

        setTitle(R.string.academy_wall);
        initialisedFields(view);

//        auth = FirebaseAuth.getInstance();
//        currentUserId = auth.getCurrentUser().getUid();
//        userRefereces = FirebaseDatabase.getInstance().getReference().child("Users");

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

            wallChatViewModel.saveMessageFromWallChat(currentUserName,userGroup,message,currentDate,currentTime);

        }
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

            String userGroup = (String) iterator.next().getValue();
            String chatDate = (String) iterator.next().getValue();
            String chatMessage = (String) iterator.next().getValue();
            String chatName = (String) iterator.next().getValue();
            String chatTime = (String) iterator.next().getValue();

            display_text_messages.append(
                    "_______________________________" + "\n" +
                    chatName + " from " + chatDate + "\n"
                    + "on " + userGroup + " at " + chatTime + "\n"
                    + "-------------------------------" + "\n" +
                        "# "    + chatMessage + "\n\n");

            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    private void getUserInfo() {

        wallChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                currentUserName=dataSnapshot.child("Name").getValue().toString();
                userGroup = dataSnapshot.child("Sector").getValue().toString();
            }
        });
    }
}
