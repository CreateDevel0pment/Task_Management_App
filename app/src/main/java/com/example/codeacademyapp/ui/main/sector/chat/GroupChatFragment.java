package com.example.codeacademyapp.ui.main.sector.chat;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.PublicMessageAdapter;
import com.example.codeacademyapp.data.model.PublicMessage;
import com.example.codeacademyapp.ui.main.sector.task.TaskActivity;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.example.codeacademyapp.ui.sign_in_up.fragments.UserInformationViewModel;
import com.example.codeacademyapp.utils.NetworkConnectivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupChatFragment extends BaseFragment {

    private ImageButton sentMessage_btn;
    private ImageView uploadFile_btn;
    private EditText userMessage_input;
    private String currentUserName, userImage;
    private String userGroup;
    private String userID;

    private String message;
    private String fileName;
    private String currentDate, currentTime;

    private Uri uri;

    private String checker;

    private ChatViewModel groupChatViewModel;
    private UserInformationViewModel userInformationViewModel;
    private ImageView new_task_btn;

    private ProgressDialog progressDialog;

    private RecyclerView chat_recycler;
    private PublicMessageAdapter adapter;
    private DatabaseReference myRef;
    private List<PublicMessage> messageList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        displayMessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_group_chat, container, false);
        userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);

        String id = userInformationViewModel.getUserId();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                    if (!Objects.requireNonNull(dataSnapshot.child("Position").getValue()).equals("Staff")) {
                        new_task_btn.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        new_task_btn = view.findViewById(R.id.new_task_button);
        new_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newTaskIntent = new Intent(getActivity(), TaskActivity.class);
                newTaskIntent.putExtra("type", "forAll");
                startActivity(newTaskIntent);
            }
        });

        initialisedFields(view);
        sentMessage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveMessageToDataBase();
                userMessage_input.setText("");
            }
        });


        uploadFile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence[] options = new CharSequence[]{
                        "Images",
                        "PDF files",
                        "Ms Word files"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select file type");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent().setAction(Intent.ACTION_GET_CONTENT);

                        switch (which) {
                            case 0:
                                checker = ".jpg";

                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Photo"), 1);
                                break;

                            case 1:
                                checker = ".pdf";

                                intent.setType("application/pdf");
                                startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 1);
                                break;

                            case 2:
                                checker = ".docx";

                                intent.setType("application/msword");
                                startActivityForResult(Intent.createChooser(intent, "Select Ms Word File"), 1);
                                break;

                            default:
                                return;
                        }
                    }
                });

                builder.show();

            }
        });

        return view;
    }

    private void SaveMessageToDataBase() {

        message = userMessage_input.getText().toString();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), "Write your message", Toast.LENGTH_SHORT).show();
        } else {
            getCurrentDateAndTime();

            checker = "";

            groupChatViewModel.saveMessage(getMessage(), "Chat Sector", userGroup);
        }
    }

    private void displayMessage() {

        userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        userInformationViewModel.getUserInformation().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentUserName = Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString();

                    userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                    userID = dataSnapshot.getRef().getKey();

                    setTitle(userGroup + " Sector");

                    if (dataSnapshot.child("image").getValue() != null) {
                        userImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                    }

                    displayMessageToGroup(userGroup);
                }
            }
        });
    }

    private void displayMessageToGroup(String userGroup) {

        groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        groupChatViewModel.displayMessage("Chat Sector", userGroup).observe(GroupChatFragment.this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                PublicMessage messages = dataSnapshot.getValue(PublicMessage.class);

                messageList.add(messages);
                adapter = new PublicMessageAdapter(messageList, getFragmentManager());
                chat_recycler.setAdapter(adapter);
                progressDialog.dismiss();

                chat_recycler.smoothScrollToPosition(Objects.requireNonNull(chat_recycler.getAdapter()).getItemCount());
            }
        });
    }

    private void initialisedFields(View view) {
        sentMessage_btn = view.findViewById(R.id.sent_message_btn);
        userMessage_input = view.findViewById(R.id.input_user_message);
        uploadFile_btn = view.findViewById(R.id.group_chat_file_btn);

        chat_recycler = view.findViewById(R.id.group_chat_recycler);
        chat_recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();


        if (NetworkConnectivity.isConnectivityNetworkAvailable(getContext())) {

            Toast connectivityToast = Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG);
            connectivityToast.setGravity(Gravity.CENTER, 0, 0);
            connectivityToast.show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {

            progressDialog.setTitle("Upload document");
            progressDialog.setMessage("Please wait. Document loading..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            getCurrentDateAndTime();

            uri = data.getData();

            if (uri != null) {

                Cursor returnCursor =
                        getContext().getContentResolver().query(uri, null, null, null, null);

                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();

                fileName = returnCursor.getString(nameIndex);

                groupChatViewModel.saveDocumentFile(getMessage(), "Chat Sector", userGroup);
            }
        }
    }

    private PublicMessage getMessage() {

        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setId(userID);
        publicMessage.setName(currentUserName);
        publicMessage.setSector(userGroup);
        publicMessage.setImage(userImage);
        publicMessage.setMessage(message);
        publicMessage.setDocType(checker);
        publicMessage.setDate(currentDate);
        publicMessage.setTime(currentTime);
        publicMessage.setDocName(fileName);
        publicMessage.setUri(uri);

        return publicMessage;
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
}
