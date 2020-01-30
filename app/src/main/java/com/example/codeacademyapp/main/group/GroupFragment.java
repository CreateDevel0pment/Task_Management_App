package com.example.codeacademyapp.main.group;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.sign_in.BaseFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
public class GroupFragment extends BaseFragment {

    public static final String TAG = "CHAT_TAG";
    private ImageButton sentMessage_btn;
    private EditText userMessage_input;
    private ScrollView scrollView;
    private TextView display_text_messages;

    private String currentGroupNAme;
    private String currentUserId;
    private String currentUserName;

    private FirebaseAuth auth;
    private DatabaseReference userRefereces, groupNameRef, groupMessageKeyRef;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (getArguments() != null) {
            currentGroupNAme = getArguments().getString("TITLE");
            setTitle(currentGroupNAme + " Group");

            groupNameRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupNAme);
            groupNameRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    if (dataSnapshot.exists()) {

                        displayMessages(dataSnapshot);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    if (dataSnapshot.exists()) {

                        displayMessages(dataSnapshot);
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

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();
        userRefereces = FirebaseDatabase.getInstance().getReference().child("Users");

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
        String messageKey = groupNameRef.push().getKey();

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


            HashMap<String, Object> groipMessageKey = new HashMap<>();
            groupNameRef.updateChildren(groipMessageKey);

            groupMessageKeyRef = groupNameRef.child(messageKey);

            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("name", currentUserName);
            messageInfoMap.put("message", message);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time", currentTime);

            groupMessageKeyRef.updateChildren(messageInfoMap);
        }
    }

    private void getUserInfo() {

        userRefereces.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    currentUserName = dataSnapshot.child("Name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        }
    }

//    private Button new_task_btn;
//    private RecyclerView recyclerView;
//    private ArrayList<String> list_of_chat_groups;
//    private GroupCategoryAdapter adapter;
//
//    private GroupChatViewModel groupChatViewModel;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.fragment_group, container, false);
//        setTitle(R.string.my_group);
//        new_task_btn = rootView.findViewById(R.id.new_task_button);
//        new_task_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                newTaskActivity();
//            }
//        });
//
//        groupChatViewModel = ViewModelProviders.of(this).get(GroupChatViewModel.class);
//
//        initializingViews(rootView);
//        retrieveAndDisplayGroups();
//
//        FloatingActionButton addNewTaskButton = rootView.findViewById(R.id.add_new_task_floating_btn);
//        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddNewTaskFragment addNewTaskFragment = new AddNewTaskFragment();
//                FragmentManager manager = getFragmentManager();
//                manager.beginTransaction().replace(R.id.main_container, addNewTaskFragment).commit();
//            }
//        });
//
//        return rootView;
//
//
//    }
//
//    private void initializingViews(View view) {
//        new_task_btn = view.findViewById(R.id.new_task_button);
//        list_of_chat_groups = new ArrayList<>();
//        adapter = new GroupCategoryAdapter(list_of_chat_groups, getFragmentManager());
//        recyclerView = view.findViewById(R.id.list_chat_groups);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//    }
//
//    private void retrieveAndDisplayGroups() {
//
//        groupChatViewModel.getGroupNames().observe(this, new Observer<Iterator<DataSnapshot>>() {
//            @Override
//            public void onChanged(Iterator<DataSnapshot> dataSnapshotIterator) {
//                final Set<String> set = new HashSet<>();
//
//                while (dataSnapshotIterator.hasNext()) {
//
//                    set.add(dataSnapshotIterator.next().getKey());
//                }
//
//                list_of_chat_groups.clear();
//                list_of_chat_groups.addAll(set);
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }
//
//    private void newTaskActivity() {
//        Intent intent = new Intent(getContext(), NewTaskActivity.class);
//        startActivity(intent);
//    }
}
