package com.example.codeacademyapp.ui.main.wall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.MessageAdapter;
import com.example.codeacademyapp.data.model.Messages;
import com.example.codeacademyapp.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateChatActivity extends AppCompatActivity {

    private String message_reciever_id,message_reciever_name,user_image,user_sector,message_sender_id;

    TextView userNAme, userLastSeen,userSector;
    CircleImageView iserImage;
    Toolbar toolbar;
    ImageButton send_message_btn;
    EditText input_message;
    List<Messages> messageList=new ArrayList<>();
    MessageAdapter adapter;
    RecyclerView user_message_list;

    FirebaseAuth auth;
    DatabaseReference roothRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);

        auth=FirebaseAuth.getInstance();
        message_sender_id=auth.getCurrentUser().getUid();
        roothRef= FirebaseDatabase.getInstance().getReference();

        message_reciever_id=getIntent().getExtras().get("visit_user_id").toString();
        message_reciever_name=getIntent().getExtras().get("visit_user_name").toString();
        user_sector=getIntent().getExtras().get("visit_user_sector").toString();
        user_image=getIntent().getExtras().get("visit_user_image").toString();

        initializedView();

        userNAme.setText(message_reciever_name);
        userSector.setText(user_sector);
        Picasso.get().load(user_image).placeholder(R.drawable.profile_image).into(iserImage);
        send_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage();

            }
        });

        displayMessage();
    }

    private void initializedView() {
        toolbar=findViewById(R.id.private_chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LayoutInflater inflater= (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBArView= inflater.inflate(R.layout.custom_chat_bar,null);
        getSupportActionBar().setCustomView(actionBArView);

        iserImage=findViewById(R.id.custom_profile_image);
        userSector=findViewById(R.id.custom_profile_sector);
        userNAme=findViewById(R.id.custom_profile_name);
        userLastSeen=findViewById(R.id.custom_user_last_seen);

        send_message_btn=findViewById(R.id.sent_private_message_btn);
        input_message=findViewById(R.id.input_private_message);

        adapter=new MessageAdapter(messageList);
        user_message_list=findViewById(R.id.private_message_list);
        user_message_list.setLayoutManager(new LinearLayoutManager(this));
        user_message_list.setAdapter(adapter);

    }

    private  void displayMessage(){

        roothRef.child("Message").child(message_sender_id).child(message_reciever_id)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        Messages messages=dataSnapshot.getValue(Messages.class);

                        messageList.add(messages);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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


    private void sendMessage(){

        String message_text=input_message.getText().toString();
        if(TextUtils.isEmpty(message_text)){

            Toast.makeText(this, "Write message", Toast.LENGTH_SHORT).show();
        }else {

            String message_sender_ref= "Message/" + message_sender_id + "/" + message_reciever_id;
            String message_reciever_ref= "Message/" + message_reciever_id + "/" + message_sender_id;

            DatabaseReference userMessageKeyRef=roothRef.child("Messages")
                    .child(message_sender_ref).child(message_reciever_ref).push();


            String messagePushId=userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", message_text);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", message_sender_id);

            Map messageBodyDetails=new HashMap();
            messageBodyDetails.put(message_sender_ref + "/" + messagePushId, messageTextBody);
            messageBodyDetails.put(message_reciever_ref + "/" + messagePushId, messageTextBody);

            roothRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if(task.isSuccessful()){

                        Toast.makeText(PrivateChatActivity.this, "message send", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(PrivateChatActivity.this, "error send message", Toast.LENGTH_SHORT).show();
                    }

                    input_message.setText("");
                }
            });
        }
    }
}
