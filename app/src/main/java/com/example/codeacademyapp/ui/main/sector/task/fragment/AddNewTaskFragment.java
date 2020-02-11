package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.AssignedUsers;
import com.example.codeacademyapp.data.model.Task;
import com.example.codeacademyapp.ui.main.sector.task.TaskViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewTaskFragment extends Fragment implements UsersToAssignDialogListener,DatePickerDialogListener{


    private EditText task_name, task_description, task_note;
    private Button create_task;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseUser userFb;
    private String userID, userGroup, taskPriority;
    private TaskViewModel taskViewModel;
    private Spinner taskPrioritySpinner;
    private Task task;
    private View rootView;
    private String currentDate;
    String endDate;

    private List<AssignedUsers> assignedUsersList;

    public AddNewTaskFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_add_new_task, container, false);

        task_name = rootView.findViewById(R.id.task_name);
        task_description = rootView.findViewById(R.id.task_desc);
        task_note = rootView.findViewById(R.id.task_note);


        ImageView datePickerImg = rootView.findViewById(R.id.date_picker_icon);
        datePickerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ImageView assignToUsersImg = rootView.findViewById(R.id.assign_to_img);
        assignToUsersImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUsersListDialog();
            }
        });

        create_task = rootView.findViewById(R.id.create_task_btn);

        taskViewModel = ViewModelProviders.of(AddNewTaskFragment.this).get(TaskViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        userFb = mAuth.getCurrentUser();
        if (userFb != null) {
            userID = userFb.getUid();
        }

        Calendar calForDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy");


        currentDate = currentDateFormat.format(calForDate.getTime());

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    userGroup = dataSnapshot.child("Sector").getValue().toString();
                }
            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    toastMessage("Successfully signed in with: " + user.getEmail());

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
            }
        };

        taskPrioritySpinner = rootView.findViewById(R.id.priorty_spinner);

        taskPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (taskPrioritySpinner.getSelectedItem().equals("High")) {
                    taskPriority = "High";
                } else if (taskPrioritySpinner.getSelectedItem().equals("Medium")) {
                    taskPriority = "Medium";
                } else if (taskPrioritySpinner.getSelectedItem().equals("Low")) {
                    taskPriority = "Low";
                } else {
                    taskPriority = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setTaskValues();
                taskViewModel.createNewTask(task);

                toastMessage("Task created");
                clearFieldsAfterCreatingTask();

            }
        });

        return rootView;
    }

    private void showDatePickerDialog(){
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.show(getFragmentManager(), "date_picker_fragemnt");
        datePickerDialogFragment.setTargetFragment(AddNewTaskFragment.this, 300);

    }

    private void showUsersListDialog() {

        UsersToAssignTaskFragment usersToAssignTaskFragment = new UsersToAssignTaskFragment();
        usersToAssignTaskFragment.show(getFragmentManager(), "fragment_alert");
        usersToAssignTaskFragment.onDestroy();
        usersToAssignTaskFragment.setTargetFragment(AddNewTaskFragment.this, 300);
    }

    private void toastMessage(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void clearFieldsAfterCreatingTask(){
        task_note.setText("");
        task_description.setText("");
        task_name.setText("");
        taskPrioritySpinner.setSelection(0);
        if(assignedUsersList!=null){
            assignedUsersList.clear();
        }

        endDate = null;
    }

    private void setTaskValues(){

        task = new Task();

        task.setName(task_name.getText().toString());
        task.setDescription(task_description.getText().toString());
        task.setNote(task_note.getText().toString());
        task.setStart_date(currentDate);
        task.setImportance(taskPriority);
        task.setGroup(userGroup);
        task.setAssignedUsers(assignedUsersList);
        task.setEndDate(endDate);
    }

    @Override
    public void passListOfUsersToAddNewTaskFragment(List<AssignedUsers> list) {
        this.assignedUsersList = list;
    }

    @Override
    public void passDateString(String date) {
        this.endDate = date;
    }
}
