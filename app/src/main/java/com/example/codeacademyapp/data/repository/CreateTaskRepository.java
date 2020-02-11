package com.example.codeacademyapp.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.data.model.CompletedBy;
import com.example.codeacademyapp.data.model.Task;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.ui.main.sector.task.fragment.TaskNameListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateTaskRepository {

    private MutableLiveData<Task> setTaskInformation;
    private MutableLiveData<TaskInformation> taskInformationMutableLiveData;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String userID;

    public CreateTaskRepository() {
        setTaskInformation = new MutableLiveData<>();
        taskInformationMutableLiveData = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
    }




    public MutableLiveData<TaskInformation> addCompletedByOnTask(TaskInformation taskInformation) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        FirebaseUser userFb = mAuth.getCurrentUser();

        if (userFb != null) {
            userID = userFb.getUid();
        }

            myRef.child("Tasks").child(taskInformation.getName())
                    .child("CompletedBy")
                    .setValue(taskInformation.getCompletedBy());


        return taskInformationMutableLiveData;
    }

    public MutableLiveData<Task> createNewTask(Task task) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        if (!task.getName().equals("") && !task.getDescription().equals("") &&
                !task.getNote().equals("")) {

            FirebaseUser userFb = mAuth.getCurrentUser();

            if (userFb != null) {
                userID = userFb.getUid();
            }

            myRef.child("Tasks").child(task.getName())
                    .child("Description")
                    .setValue(task.getDescription());

            myRef.child("Tasks").child(task.getName())
                    .child("Name")
                    .setValue(task.getName());

            myRef.child("Tasks").child(task.getName())
                    .child("Note")
                    .setValue(task.getNote());

            myRef.child("Tasks").child(task.getName())
                    .child("Sector")
                    .setValue(task.getGroup());

            myRef.child("Tasks").child(task.getName())
                    .child("TimeCreated")
                    .setValue(task.getStart_date());

            myRef.child("Tasks").child(task.getName())
                    .child("TaskPriority")
                    .setValue(task.getImportance());

            myRef.child("Tasks").child(task.getName())
                    .child("AssignedUsers")
                    .setValue(task.getAssignedUsers());

            myRef.child("Tasks").child(task.getName())
                    .child("EndDate")
                    .setValue(task.getEndDate());

            myRef.child("Tasks").child(task.getName())
                    .child("TaskCreator")
                    .setValue(userID);

        }
        return setTaskInformation;
    }

}


