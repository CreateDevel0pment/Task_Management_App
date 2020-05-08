package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.Task;
import com.example.codeacademyapp.ui.main.sector.task.TaskNotificationViewModel;
import com.example.codeacademyapp.ui.main.sector.task.TaskViewModel;
import com.example.codeacademyapp.ui.main.sector.task.fragment.listeners.DatePickerDialogListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddNewTaskFragment extends Fragment implements DatePickerDialogListener {

    private EditText task_name, task_description;
    private TextView assignedDate, docNameTV;
    private String userID, userGroup, taskPriority, extrasUserName, extrasUserId;
    private TaskViewModel taskViewModel;
    private View rootView;
    private String currentDate, endDate;
    private String checker,fileName;
    private TaskNotificationViewModel taskNotificationViewModel;
    private ImageView uploadFile;
    private ProgressDialog progressDialog;
    private Uri uri;
    private Button createTaskButton;


    public AddNewTaskFragment(String userName, String extrasUserId) {
        this.extrasUserName = userName;
        this.extrasUserId = extrasUserId;
    }

    public AddNewTaskFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_add_new_task, container, false);

        task_name = rootView.findViewById(R.id.task_name);
        task_description = rootView.findViewById(R.id.task_desc);
        docNameTV = rootView.findViewById(R.id.doc_name_newTask);
        TextView assignedUserNameTV = rootView.findViewById(R.id.assigned_user_name);
        LinearLayout assignUserLayout = rootView.findViewById(R.id.assigned_user_linear);
        uploadFile = rootView.findViewById(R.id.task_file_btn_upload);
        assignedDate = rootView.findViewById(R.id.assigned_end_date);
        progressDialog = new ProgressDialog(getContext());
        createTaskButton = rootView.findViewById(R.id.create_task_btn);

        uploadFile();

        ImageView datePickerImg = rootView.findViewById(R.id.date_picker_icon);
        datePickerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        if (extrasUserName != null) {
            assignedUserNameTV.setText(extrasUserName);
        } else {
            assignUserLayout.setVisibility(View.GONE);
        }

        taskNotificationViewModel = ViewModelProviders.of(AddNewTaskFragment.this).get(TaskNotificationViewModel.class);
        taskViewModel = ViewModelProviders.of(AddNewTaskFragment.this).get(TaskViewModel.class);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser userFb = mAuth.getCurrentUser();
        if (userFb != null) {
            userID = userFb.getUid();
        }

        Calendar calForDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        currentDate = currentDateFormat.format(calForDate.getTime());

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                }
            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (extrasUserId != null) {
                    taskViewModel.createAssignedTask(setTaskValues());
                } else {
                    taskViewModel.createGroupTask(setTaskValues());
                }
                progressDialog.setTitle("Upload document");
                progressDialog.setMessage("Please wait. Document loading..");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                taskViewModel.checkForDoc(userGroup).observe(AddNewTaskFragment.this, new Observer<DataSnapshot>() {
                    @Override
                    public void onChanged(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                    }
                });

                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        return rootView;
    }

    private void showDatePickerDialog() {
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        assert getFragmentManager() != null;{
            datePickerDialogFragment.show(getFragmentManager(), "date_picker_fragment");
        }
        datePickerDialogFragment.setTargetFragment(AddNewTaskFragment.this, 300);
    }

    private Task setTaskValues() {
        Task task = new Task();
        task.setName(task_name.getText().toString());
        task.setDescription(task_description.getText().toString());
        task.setStart_date(currentDate);
        task.setImportance(taskPriority);
        task.setGroup(userGroup);
        task.setEndDate(endDate);
        task.setDocType(checker);
        task.setDocName(fileName);
        task.setUri(uri);
        if (extrasUserId != null) {
            task.setAssignedUserId(extrasUserId);
        }

        if (extrasUserId!=null){
            taskNotificationViewModel.sendTaskNotification(userID, extrasUserId);
        }
        return task;
    }

    @Override
    public void passDateString(String date) {
        this.endDate = date;
        assignedDate.setText(date);
    }

    private void uploadFile(){
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] options = new CharSequence[]{
//                        "Images",
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
//                            case 0:
//                                checker = ".jpg";
//
//                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent, "Select Photo"), 1);
//                                break;

                            case 0:
                                checker = ".pdf";

                                intent.setType("application/pdf");
                                startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 1);
                                break;

                            case 1:
                                checker = ".docx";

                                intent.setType("application/msword");
                                startActivityForResult(Intent.createChooser(intent, "Select Ms Word File"), 1);
                                break;

                            default:
                        }
                    }
                });

                builder.show();

            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {

            uri = data.getData();

            if (uri != null) {

                Cursor returnCursor =
                        getContext().getContentResolver().query(uri, null, null, null, null);

                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();

                fileName = returnCursor.getString(nameIndex);
                docNameTV.setText(fileName);
                LinearLayout docNameLL = rootView.findViewById(R.id.checkbox_doc_linear);
                docNameLL.setVisibility(View.VISIBLE);
            }
        }

    }
}
