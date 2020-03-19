package com.example.codeacademyapp.ui.main.wall;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private String checker,uriString;
    private String currentDate, currentTime;

    String fileName;

    private Uri uri;

    TextView userNAme, userLastSeen, userSector;
    CircleImageView iserImage;
    Toolbar toolbar;
    ImageButton send_message_btn;
    ImageView add_new_task, sedn_doc;
    EditText input_message;
    List<PrivateMessages> messageList = new ArrayList<>();
    PrivateMessageAdapter adapter;
    RecyclerView user_message_list;
    ProgressDialog progressDialog;

    private DatabaseReference myRef;

    ChatViewModel chatViewModel;

    FirebaseAuth auth;
    DatabaseReference roothRef;
    private StorageTask uploadDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);
        initializedView();

        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        auth = FirebaseAuth.getInstance();
        message_sender_id = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        roothRef = FirebaseDatabase.getInstance().getReference();

        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(message_sender_id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (!dataSnapshot.child("Position").getValue().equals("Staff")) {
                        add_new_task.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Intent intent = getIntent();
        message_reciever_id = Objects.requireNonNull(intent.getExtras().get("visit_user_id")).toString();
        String message_reciever_name = Objects.requireNonNull(intent.getExtras().get("visit_user_name")).toString();
        user_sector = Objects.requireNonNull(intent.getExtras().get("visit_user_sector")).toString();

        if (intent.getExtras().get("visit_user_image") != null) {
            user_image = Objects.requireNonNull(intent.getExtras().get("visit_user_image")).toString();
        }

        add_new_task = findViewById(R.id.private_new_task_button);
        add_new_task.setOnClickListener(new View.OnClickListener() {
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

        sedn_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence options[] = new CharSequence[]{

                        "Images",
                        "PDF files",
                        "Ms Word files"
                };

                AlertDialog.Builder builder= new AlertDialog.Builder(PrivateChatActivity.this);
                builder.setTitle("Select file type");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intentDoc=new Intent().setAction(Intent.ACTION_GET_CONTENT);

                        switch (which){

                            case 0:
                                checker=".jpg";

                                intentDoc.setType("image/*");
                                startActivityForResult(Intent.createChooser(intentDoc,"Select Photo"),2);
                                break;

                            case 1:
                                checker=".pdf";
                                intentDoc.setType("application/pdf");
                                startActivityForResult(Intent.createChooser(intentDoc,"Select PDF FIle"),2);
                                break;

                            case 2:
                                checker=".docx";
                                intentDoc.setType("application/msword");
                                startActivityForResult(Intent.createChooser(intentDoc,"Select Ms Word File"),2);
                                break;

                            default:
                        }
                    }
                });
                builder.show();

            }
        });
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


            final String messagePushId = userMessageKeyRef.getKey();

            HashMap <String, String> messageTextBody = new HashMap();
            messageTextBody.put("message", message_text);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", message_sender_id);
            messageTextBody.put("to", message_reciever_id);
            messageTextBody.put("messageId",messagePushId);
            messageTextBody.put("date", currentDate);
            messageTextBody.put("time", currentTime);

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

    @SuppressLint("InflateParams")
    private void initializedView() {

        toolbar = findViewById(R.id.private_chat_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

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

        send_message_btn = findViewById(R.id.private_sent_message_btn);
        input_message = findViewById(R.id.private_input_user_message);
        sedn_doc = findViewById(R.id.private_chat_file_btn);

        user_message_list = findViewById(R.id.private_message_list);
        user_message_list.setLayoutManager(new LinearLayoutManager(this));
        progressDialog= new ProgressDialog(PrivateChatActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null){

            progressDialog.setTitle("Upload document");
            progressDialog.setMessage("Please wait. Document loading..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            uri = data.getData();

            if(uri!=null) {

                Cursor returnCursor =
                        Objects.requireNonNull(PrivateChatActivity.this)
                                .getContentResolver()
                                .query(uri, null, null, null, null);

                int nameIndex = 0;
                if (returnCursor != null) {
                    nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                }
                if (returnCursor != null) {
                    returnCursor.moveToFirst();
                }
                if (returnCursor != null) {
                    fileName = returnCursor.getString(nameIndex);
                }

                if (!checker.equals(".jpg")) {

                    saveDocumentFile();

                } else if (checker.equals(".jpg")) {

                    getCurrentDateAndTime();

                    saveDocumentFile();

                } else {

                    progressDialog.dismiss();
                    Toast.makeText(this, "Error file selected", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void getCurrentDateAndTime() {

        Calendar calForDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd MMM,yyyy");
        currentDate = currentDateFormat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm");
        currentTime = currentTimeFormat.format(calForTime.getTime());
    }

    private void saveDocumentFile(){

        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Send Doc Files");

        final String message_sender_ref = "Chat Private/" + message_sender_id + "/" + message_reciever_id;
        final String message_reciever_ref = "Chat Private/" + message_reciever_id + "/" + message_sender_id;

        DatabaseReference userMessageKeyRef = roothRef.child("Chat Private")
                .child(message_sender_ref).child(message_reciever_ref).push();


        final String messagePushId = userMessageKeyRef.getKey();

        final StorageReference filePath = storageReference.child(messagePushId + checker);

        uploadDoc = filePath.putFile(uri);
        uploadDoc.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {


                if(!task.isSuccessful()){

                    throw task.getException();
                }

                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>()  {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if(task.isSuccessful()){

                    Uri downloadUrl = task.getResult();
                    if (downloadUrl != null) {
                        uriString = downloadUrl.toString();
                    }

                    Map<String, String> messageTextBody = new HashMap();
                    messageTextBody.put("message", uriString);
                    messageTextBody.put("name", fileName);
                    messageTextBody.put("type", checker);
                    messageTextBody.put("from", message_sender_id);
                    messageTextBody.put("to", message_reciever_id);
                    messageTextBody.put("messageId",messagePushId);
                    messageTextBody.put("date", currentDate);
                    messageTextBody.put("time", currentTime);

                    Map messageBodyDetails = new HashMap();
                    messageBodyDetails.put(message_sender_ref + "/" + messagePushId, messageTextBody);
                    messageBodyDetails.put(message_reciever_ref + "/" + messagePushId, messageTextBody);

                    roothRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                Toast.makeText(PrivateChatActivity.this, "message send", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(PrivateChatActivity.this, "error send message", Toast.LENGTH_SHORT).show();
                            }

                            input_message.setText("");
                        }
                    });
                }
            }
        });
    }
}
