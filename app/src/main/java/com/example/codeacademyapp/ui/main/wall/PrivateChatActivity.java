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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.MessageAdapter;
import com.example.codeacademyapp.data.model.PublicMessage;
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.example.codeacademyapp.ui.main.sector.task.TaskActivity;
import com.example.codeacademyapp.ui.sign_in_up.fragments.UserInformationViewModel;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateChatActivity extends AppCompatActivity {

    private String message_reciever_id;
    private String user_image;
    private String user_sector;
    private String checker;
    private String currentDate, currentTime;
    private String userPosition;
    private String message_text;
    private String userGroup;
    private String fileName;

    private String id, currentUserName, userImage;

    private Uri uri;

    TextView userNAme, userLastSeen, userSector;
    CircleImageView iserImage;
    Toolbar toolbar;
    ImageButton send_message_btn;
    ImageView add_new_task, sedn_doc;
    EditText input_message;
    List<PublicMessage> messageList = new ArrayList<>();
    MessageAdapter adapter;
    RecyclerView user_message_list;
    ProgressDialog progressDialog;

    ChatViewModel chatViewModel;
    UserInformationViewModel userInformationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);
        initializedView();
        getUserInfo();

        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

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

        displayMessage();

        sedn_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence options[] = new CharSequence[]{

                        "Images",
                        "PDF files",
                        "Ms Word files"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(PrivateChatActivity.this);
                builder.setTitle("Select file type");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intentDoc = new Intent().setAction(Intent.ACTION_GET_CONTENT);

                        switch (which) {

                            case 0:
                                checker = ".jpg";

                                intentDoc.setType("image/*");
                                startActivityForResult(Intent.createChooser(intentDoc, "Select Photo"), 2);
                                break;

                            case 1:
                                checker = ".pdf";
                                intentDoc.setType("application/pdf");
                                startActivityForResult(Intent.createChooser(intentDoc, "Select PDF FIle"), 2);
                                break;

                            case 2:
                                checker = ".docx";
                                intentDoc.setType("application/msword");
                                startActivityForResult(Intent.createChooser(intentDoc, "Select Ms Word File"), 2);
                                break;

                            default:
                        }
                    }
                });
                builder.show();

            }
        });
    }

    private void displayMessage() {


        chatViewModel.displayMessage("Chat Private", "").observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                PublicMessage messages = dataSnapshot.getValue(PublicMessage.class);

                messageList.add(messages);
                adapter = new MessageAdapter(messageList, getSupportFragmentManager(),"");
                user_message_list.setAdapter(adapter);

                user_message_list.smoothScrollToPosition(user_message_list.getAdapter().getItemCount());

            }
        });
    }

    private void sendMessage() {

        message_text = input_message.getText().toString();
        if (TextUtils.isEmpty(message_text)) {

            Toast.makeText(this, "Write message", Toast.LENGTH_SHORT).show();
        } else {

            getCurrentDateAndTime();

            checker = "";

            chatViewModel.saveMessage(getMessage(), "Chat Private", "");

            input_message.setText("");
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
        progressDialog = new ProgressDialog(PrivateChatActivity.this);

    }

    private void getUserInfo() {

        userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        userInformationViewModel.getUserInformation().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    currentUserName = Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString();
                    userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                    userPosition = Objects.requireNonNull(dataSnapshot.child("Position").getValue()).toString();
                    id = dataSnapshot.getRef().getKey();

                    if (dataSnapshot.child("image").getValue() != null) {
                        userImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                    }

                    if (!userPosition.equals("Staff")) {
                        add_new_task.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && data != null) {

            progressDialog.setTitle("Upload document");
            progressDialog.setMessage("Please wait. Document loading..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            getCurrentDateAndTime();

            uri = data.getData();
            if (uri != null) {
                Cursor returnCursor =
                        Objects.requireNonNull(this)
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

                chatViewModel.saveDocumentFile(getMessage(), "Chat Private","");
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

    private PublicMessage getMessage() {

        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setId(id);
        publicMessage.setName(currentUserName);
        publicMessage.setSector(userGroup);
        publicMessage.setImage(userImage);
        publicMessage.setMessage(message_text);
        publicMessage.setDocType(checker);
        publicMessage.setDate(currentDate);
        publicMessage.setTime(currentTime);
        publicMessage.setDocName(fileName);
        publicMessage.setUri(uri);

        return publicMessage;
    }
}
