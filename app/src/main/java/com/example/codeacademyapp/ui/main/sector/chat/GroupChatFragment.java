package com.example.codeacademyapp.ui.main.sector.chat;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.codeacademyapp.data.model.ModelFirebase;
import com.example.codeacademyapp.ui.main.sector.task.TaskActivity;
import com.example.codeacademyapp.ui.main.wall.PrivateChatActivity;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupChatFragment extends BaseFragment {

    private ImageButton sentMessage_btn;
    private EditText userMessage_input;
    private String currentUserName, userImage;
    private String userGroup;
    private String userProfileImage;
    private String profileImageE;

    private ChatViewModel groupChatViewModel;

    private DatabaseReference usersRef;


    private RecyclerView face_profile_recycler, chat_recycler;
    private MessageGroupAdapter adapter;
    private List<MessageFromGroup> messageList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        groupChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                    setTitle(userGroup + " Sector");

                    groupChatViewModel.displayMessageToGroup(userGroup).observe(GroupChatFragment.this, new Observer<DataSnapshot>() {
                        @Override
                        public void onChanged(DataSnapshot dataSnapshot) {

                            MessageFromGroup messages = dataSnapshot.getValue(MessageFromGroup.class);

                            messageList.add(messages);
                            adapter = new MessageGroupAdapter(messageList);
                            chat_recycler.setAdapter(adapter);

                            chat_recycler.smoothScrollToPosition(chat_recycler.getAdapter().getItemCount());
                        }
                    });
                }
            }
        });
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
        getUserInfo();
        dispalyImage();

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

            groupChatViewModel.saveMessageFromGroupChat(userGroup, currentUserName, userImage, message, currentDate, currentTime);

        }
    }

    private void getUserInfo() {

        groupChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    currentUserName = dataSnapshot.child("Name").getValue().toString();
                    if(userImage != null){

                        userImage = dataSnapshot.child("image").getValue().toString();
                    }
                }
            }
        });

    }

    private void initialisedFields(View view) {
        sentMessage_btn = view.findViewById(R.id.sent_message_btn);
        userMessage_input = view.findViewById(R.id.input_user_message);

        face_profile_recycler = view.findViewById(R.id.group_face_recycler_);
        face_profile_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        chat_recycler = view.findViewById(R.id.group_chat_recycler);
        chat_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profileImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);


            profileImage = itemView.findViewById(R.id.item_image);

        }
    }

    private void dispalyImage() {

        FirebaseRecyclerOptions<ModelFirebase> options =
                new FirebaseRecyclerOptions.Builder<ModelFirebase>()
                        .setQuery(usersRef, ModelFirebase.class)
                        .build();

        FirebaseRecyclerAdapter<ModelFirebase, ImageViewHolder> adapter = new FirebaseRecyclerAdapter<ModelFirebase, ImageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ImageViewHolder holder, int position, @NonNull ModelFirebase model) {

                final String usersIds = getRef(position).getKey();

                if (usersIds != null) {
                    usersRef.child(usersIds).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild("image")) {

                                userProfileImage = dataSnapshot.child("image").getValue().toString();

                                Picasso.get().load(userProfileImage)
                                        .placeholder(R.drawable.profile_image)
                                        .into(holder.profileImage);
                            }else {

                                Picasso.get().load(R.drawable.profile_image)
                                        .into(holder.profileImage);

                            }

                            final String userName = dataSnapshot.child("Name").getValue().toString();
                            final String userSector = dataSnapshot.child("Sector").getValue().toString();

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getContext(), PrivateChatActivity.class);

                                    if (dataSnapshot.hasChild("image")) {

                                        profileImageE = dataSnapshot.child("image").getValue().toString();
                                        intent.putExtra("visit_user_image", profileImageE);
                                    }

                                    intent.putExtra("visit_user_id", usersIds);
                                    intent.putExtra("visit_user_name", userName);
                                    intent.putExtra("visit_user_sector", userSector);
                                    startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @NonNull
            @Override
            public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
                return new ImageViewHolder(view);
            }
        };

        face_profile_recycler.setAdapter(adapter);
        adapter.startListening();

    }
}
