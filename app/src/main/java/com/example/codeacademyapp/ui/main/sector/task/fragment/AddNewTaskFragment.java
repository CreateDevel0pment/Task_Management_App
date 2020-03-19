package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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
    TextView assignedDate;
    private String userID, userGroup, taskPriority, extrasUserName, extrasUserId;
    private TaskViewModel taskViewModel;
    private View rootView;
    private String currentDate, endDate;
    private TaskNotificationViewModel taskNotificationViewModel;


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
        TextView assignedUserNameTV = rootView.findViewById(R.id.assigned_user_name);
        LinearLayout assignUserLayout = rootView.findViewById(R.id.assigned_user_linear);
        LinearLayout checkBoxLayout = rootView.findViewById(R.id.checkbox_users_linear);
        assignedDate = rootView.findViewById(R.id.assigned_end_date);

        if (extrasUserName != null) {
            assignedUserNameTV.setText(extrasUserName);
            checkBoxLayout.setVisibility(View.GONE);
        } else {
            assignUserLayout.setVisibility(View.GONE);
        }

        taskNotificationViewModel = ViewModelProviders.of(AddNewTaskFragment.this).get(TaskNotificationViewModel.class);

        ImageView datePickerImg = rootView.findViewById(R.id.date_picker_icon);
        datePickerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Button create_task = rootView.findViewById(R.id.create_task_btn);

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

        RadioButton priorityBtnHigh = rootView.findViewById(R.id.radiobtn_priority_high);
        RadioButton priorityBtnMedium = rootView.findViewById(R.id.radiobtn_priority_medium);
        RadioButton priorityBtnLow = rootView.findViewById(R.id.radiobtn_priority_low);


        priorityBtnHigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    taskPriority = "High";
                } else {
                    taskPriority = null;
                }
            }
        });


        priorityBtnMedium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    taskPriority = "Medium";
                } else {
                    taskPriority = null;
                }
            }
        });

        priorityBtnLow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    taskPriority = "Low";
                } else {
                    taskPriority = null;
                }
            }
        });

        create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (extrasUserId != null) {
                    taskViewModel.createAssignedTask(setTaskValues());
                } else {
                    taskViewModel.createGroupTask(setTaskValues());
                }
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
}
