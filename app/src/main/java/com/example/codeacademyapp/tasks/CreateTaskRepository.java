package com.example.codeacademyapp.tasks;

import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.model.Task;
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
    }

    public MutableLiveData<Task> createNewTask(Task task) {

        if (!task.getName().equals("") && !task.getDescription().equals("") &&
                !task.getNote().equals("")) {

        FirebaseUser userFb = mAuth.getCurrentUser();
        String userID;
        if (userFb != null) {
            userID = userFb.getUid();

            myRef.child("Tasks").child(userID).child(task.getName()).child("Task Information")
                    .child("Description")
                    .setValue(task.getDescription());
            myRef.child("Tasks").child(userID).child(task.getName()).child("Task Information")
                    .child("Task Note")
                    .setValue(task.getNote());
//            myRef.child("Tasks").child(task.getId()).child("Task Information")
//                    .child("Task State")
//                    .setValue(task.getState());
//            myRef.child("Tasks").child(task.getId()).child("Task Information")
//                    .child("Task Importance")
//                    .setValue(task.getImportance());
//            myRef.child("Tasks").child(task.getId()).child("Task Information")
//                    .child("Task Due Date")
//                    .setValue(task.getDue_date());
//            myRef.child("Tasks").child(task.getId()).child("Task Information")
//                    .child("Task Start Date")
//                    .setValue(task.getStart_date());
            myRef.child("Tasks").child("Task Information")
                    .child("Creator")
                    .setValue(userID);
        }
        }
        return setTaskInformation;
    }
}

