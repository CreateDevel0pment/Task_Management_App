package com.example.codeacademyapp.ui.main.wall;


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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.MessageWallAdapter;
import com.example.codeacademyapp.data.model.PublicMessage;
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.example.codeacademyapp.ui.sign_in_up.fragments.UserInformationViewModel;
import com.example.codeacademyapp.utils.NetworkConnectivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class WallFragment extends BaseFragment {

    private ImageButton sentMessage_btn;
    private ImageButton uploadFile_btn;
    private EditText userMessage_input;

    private ProgressDialog loadingDialog;

    private String currentUserName, id;
    private String userGroup, userImage;

    private String checker = "";
    private String currentDate, currentTime;
    private String myUrl = "";

    private StorageTask uploadTask;
    private Uri fileUri;


    private View view;

    private ChatViewModel wallChatViewModel;
    private UserInformationViewModel userInformationViewModel;

    private RecyclerView recyclerView;
    private MessageWallAdapter adapter;

    private List<PublicMessage> messageList = new ArrayList<>();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        wallChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        wallChatViewModel.displayMessageToWall().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                PublicMessage messages = dataSnapshot.getValue(PublicMessage.class);

                messageList.add(messages);
                if (messageList.size() > 0) {
                    adapter = new MessageWallAdapter(messageList);
                    recyclerView.setAdapter(adapter);

                    loadingDialog.dismiss();
                }

                recyclerView.smoothScrollToPosition(Objects.requireNonNull(recyclerView.getAdapter()).getItemCount());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            return view;
        }

        view = inflater.inflate(R.layout.fragment_wall, container, false);

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

        String message = userMessage_input.getText().toString();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), "Write your message", Toast.LENGTH_SHORT).show();
        } else {

            Calendar calForDate = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd MMM,yyyy");

            currentDate = currentDateFormat.format(calForDate.getTime());


            Calendar calForTime = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm");

            currentTime = currentTimeFormat.format(calForTime.getTime());

            checker="";

            wallChatViewModel.saveMessageFromWallChat(id, currentUserName, userGroup, userImage, message, checker, currentDate, currentTime);

        }
    }

    private void getUserInfo() {

        userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        userInformationViewModel.getUserInformation().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    currentUserName = Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString();
                    userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                    id = dataSnapshot.getRef().getKey();

                    if (dataSnapshot.child("image").getValue() != null) {
                        userImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();

                    }
                }
            }
        });
    }

    private void initializedView(View view) {
        uploadFile_btn = view.findViewById(R.id.upload_file_btn);
        sentMessage_btn = view.findViewById(R.id.sent_message_btn);
        userMessage_input = view.findViewById(R.id.input_user_message);
        recyclerView = view.findViewById(R.id.group_chat_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadingDialog = new ProgressDialog(getContext());
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

            loadingDialog.setTitle("Upload document");
            loadingDialog.setMessage("Please wait. Document loading..");
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();


            Calendar calForDate = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd MMM,yyyy");

            currentDate = currentDateFormat.format(calForDate.getTime());


            Calendar calForTime = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm");

            currentTime = currentTimeFormat.format(calForTime.getTime());

            Uri uri = data.getData();

            if (uri != null) {
                String mimeType = Objects.requireNonNull(getContext()).getContentResolver().getType(uri);

                Cursor returnCursor =
                        getContext().getContentResolver().query(uri, null, null, null, null);

                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();

                String fileName = returnCursor.getString(nameIndex);

                wallChatViewModel.saveDocFromWallChat(id, currentUserName, userGroup, userImage, uri, checker,fileName, currentDate, currentTime);
            }

        }
    }
}
