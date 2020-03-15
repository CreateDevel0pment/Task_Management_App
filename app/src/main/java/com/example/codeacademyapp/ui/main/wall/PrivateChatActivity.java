package com.example.codeacademyapp.ui.main.wall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.PrivateMessageAdapter;
import com.example.codeacademyapp.data.model.PrivateMessages;
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.example.codeacademyapp.ui.main.sector.task.TaskActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PrivateChatActivity extends AppCompatActivity {

    private String message_reciever_id;
    private String user_image;
    private String user_sector;
    private String message_sender_id;

    TextView userNAme, userLastSeen, userSector;
    CircleImageView iserImage;
    Toolbar toolbar;
    ImageButton send_message_btn;
    EditText input_message;
    List<PrivateMessages> messageList = new ArrayList<>();
    PrivateMessageAdapter adapter;
    RecyclerView user_message_list;
    FloatingActionButton newPersonalTaskBtn;
    private DatabaseReference myRef;

    ChatViewModel chatViewModel;

    FirebaseAuth auth;
    DatabaseReference roothRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);
        initializedView();

        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        auth = FirebaseAuth.getInstance();
        message_sender_id = auth.getCurrentUser().getUid();
        roothRef = FirebaseDatabase.getInstance().getReference();



        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(message_sender_id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if(!dataSnapshot.child("Position").getValue().equals("Staff")){
                        newPersonalTaskBtn.show();
                    }
                }
            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Intent intent = getIntent();
        message_reciever_id = intent.getExtras().get("visit_user_id").toString();
        String message_reciever_name = intent.getExtras().get("visit_user_name").toString();
        user_sector = intent.getExtras().get("visit_user_sector").toString();

        if (intent.getExtras().get("visit_user_image") != null) {
            user_image = intent.getExtras().get("visit_user_image").toString();
        }


        newPersonalTaskBtn = findViewById(R.id.new_personal_task_button);
        newPersonalTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newTaskIntent = new Intent(PrivateChatActivity.this, TaskActivity.class);
                newTaskIntent.putExtra("id", message_reciever_id);
                startActivity(newTaskIntent);
        }
        });


        userNAme.setText(message_reciever_name);
        userSector.setText(user_sector);


        Picasso.get().load(user_image)
                .placeholder(R.drawable.astronaut)
                .into(iserImage);

        send_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage();

            }
        });

        displayMessage(message_reciever_id);
    }

    private void displayMessage(String message_reciever_id) {

        chatViewModel.displayMessageToPrivateChat(message_reciever_id).observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                PrivateMessages messages = dataSnapshot.getValue(PrivateMessages.class);

                messageList.add(messages);
                adapter = new PrivateMessageAdapter(messageList);
                user_message_list.setAdapter(adapter);

                user_message_list.smoothScrollToPosition(user_message_list.getAdapter().getItemCount());

            }
        });
    }

    private void sendMessage() {

        String message_text = input_message.getText().toString();
        if (TextUtils.isEmpty(message_text)) {

            Toast.makeText(this, "Write message", Toast.LENGTH_SHORT).show();
        } else {

            String message_sender_ref = "Chat Private/" + message_sender_id + "/" + message_reciever_id;
            String message_reciever_ref = "Chat Private/" + message_reciever_id + "/" + message_sender_id;

            DatabaseReference userMessageKeyRef = roothRef.child("Chat Private")
                    .child(message_sender_ref).child(message_reciever_ref).push();


            String messagePushId = userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", message_text);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", message_sender_id);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(message_sender_ref + "/" + messagePushId, messageTextBody);
            messageBodyDetails.put(message_reciever_ref + "/" + messagePushId, messageTextBody);

            roothRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (task.isSuccessful()) {

                        Toast.makeText(PrivateChatActivity.this, "message send", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(PrivateChatActivity.this, "error send message", Toast.LENGTH_SHORT).show();
                    }

                    input_message.setText("");
                }
            });
        }
    }

    private void initializedView() {

        toolbar = findViewById(R.id.private_chat_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBArView = null;
        if (inflater != null) {
            actionBArView = inflater.inflate(R.layout.custom_chat_bar, null);
        }
        getSupportActionBar().setCustomView(actionBArView);

        iserImage = findViewById(R.id.custom_profile_image);
        userSector = findViewById(R.id.custom_profile_sector);
        userNAme = findViewById(R.id.custom_profile_name);
        userLastSeen = findViewById(R.id.custom_user_last_seen);

        send_message_btn = findViewById(R.id.sent_private_message_btn);
        input_message = findViewById(R.id.input_private_message);

        user_message_list = findViewById(R.id.private_message_list);
        user_message_list.setLayoutManager(new LinearLayoutManager(this));

    }
}
