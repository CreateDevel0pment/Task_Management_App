package com.example.codeacademyapp.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.data.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTaskRepository {

    private MutableLiveData<Task> setTaskInformation;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;


    public CreateTaskRepository() {
        setTaskInformation = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<Task> createNewTask(Task task) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        if (!task.getName().equals("") && !task.getDescription().equals("") &&
                !task.getNote().equals("")) {


        FirebaseUser userFb = mAuth.getCurrentUser();
        String userID;
        if (userFb != null) {
            userID = userFb.getUid();

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

        }
        }
        return setTaskInformation;
    }



}


