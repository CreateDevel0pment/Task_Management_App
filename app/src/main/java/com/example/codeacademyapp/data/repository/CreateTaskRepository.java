package com.example.codeacademyapp.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.data.model.Task;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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

        DatabaseReference taskRef;
        taskRef = myRef.child("Tasks").push();
        if (taskRef.getKey() == null) {
            return taskInformationMutableLiveData;
        }

        myRef.child("Tasks").child("CompletedTasks").child(taskInformation.getSector())
                .child(taskRef.getKey())
                .child("CompletedBy")
                .setValue(taskInformation.getCompletedBy());

        myRef.child("Tasks").child("CompletedTasks").child(taskInformation.getSector()).child(taskRef.getKey())
                .child("Description")
                .setValue(taskInformation.getDescription());

        myRef.child("Tasks").child("CompletedTasks").child(taskInformation.getSector()).child(taskRef.getKey())
                .child("Name")
                .setValue(taskInformation.getName());

        myRef.child("Tasks").child("CompletedTasks").child(taskInformation.getSector()).child(taskRef.getKey())
                .child("TimeCreated")
                .setValue(taskInformation.getTimeCreated());

        myRef.child("Tasks").child("CompletedTasks").child(taskInformation.getSector()).child(taskRef.getKey())
                .child("TaskPriority")
                .setValue(taskInformation.getTaskPriority());

        myRef.child("Tasks").child("CompletedTasks").child(taskInformation.getSector()).child(taskRef.getKey())
                .child("EndDate")
                .setValue(taskInformation.getEndDate());

        myRef.child("Tasks").child("GroupTasks").child(taskInformation.getSector()).child(taskInformation.getTaskRef())
                .removeValue();

        myRef.child("Users").child(userID).child("Tasks").child(taskInformation.getTaskRef())
                .removeValue();

        return taskInformationMutableLiveData;
    }

    public MutableLiveData<Task> createGroupTask(Task task) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        if (!task.getName().equals("") && !task.getDescription().equals("")
        ) {

            FirebaseUser userFb = mAuth.getCurrentUser();

            if (userFb != null) {
                userID = userFb.getUid();
            }

            DatabaseReference taskRef = myRef.child("Tasks").push();
            if (taskRef.getKey() == null) {
                return setTaskInformation;
            }

//            HashMap<String, String> createTaskMap= new HashMap();
//            createTaskMap.put("Description",task.getDescription());
//            createTaskMap.put("Name",task.getDescription());
//            createTaskMap.put("Sector",task.getGroup());
//            createTaskMap.put("TimeCreated",task.getStart_date());
//            createTaskMap.put("EndDate",task.getEndDate());
//            createTaskMap.put("TaskCreator",userID);
//            createTaskMap.put("TaskRef",taskRef.getKey());
//
//            myRef.child("Tasks").child("GroupTasks")
//                    .child(task.getGroup())
//                    .child(taskRef.getKey())
//                    .setValue(createTaskMap);


            myRef.child("Tasks").child("GroupTasks").child(task.getGroup()).child(taskRef.getKey())
                    .child("Description")
                    .setValue(task.getDescription());

            myRef.child("Tasks").child("GroupTasks").child(task.getGroup()).child(taskRef.getKey())
                    .child("Name")
                    .setValue(task.getName());

            myRef.child("Tasks").child("GroupTasks").child(task.getGroup()).child(taskRef.getKey())
                    .child("Sector")
                    .setValue(task.getGroup());

            myRef.child("Tasks").child("GroupTasks").child(task.getGroup()).child(taskRef.getKey())
                    .child("TimeCreated")
                    .setValue(task.getStart_date());

            myRef.child("Tasks").child("GroupTasks").child(task.getGroup()).child(taskRef.getKey())
                    .child("TaskPriority")
                    .setValue(task.getImportance());

            myRef.child("Tasks").child("GroupTasks").child(task.getGroup()).child(taskRef.getKey())
                    .child("EndDate")
                    .setValue(task.getEndDate());

            myRef.child("Tasks").child("GroupTasks").child(task.getGroup()).child(taskRef.getKey())
                    .child("TaskCreator")
                    .setValue(userID);

            myRef.child("Tasks").child("GroupTasks").child(task.getGroup()).child(taskRef.getKey())
                    .child("TaskRef")
                    .setValue(taskRef.getKey());

        }
        return setTaskInformation;
    }


    public MutableLiveData<Task> createAssignedTask(Task task) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        if (!task.getName().equals("") && !task.getDescription().equals("")) {

            FirebaseUser userFb = mAuth.getCurrentUser();

            if (userFb != null) {
                userID = userFb.getUid();
            }

            DatabaseReference taskRef = myRef.child("Tasks").push();
            if (taskRef.getKey() == null) {
                return setTaskInformation;
            }

            myRef.child("Users").child(task.getAssignedUserId()).child("Tasks").child(taskRef.getKey())
                    .child("Description")
                    .setValue(task.getDescription());

            myRef.child("Users").child(task.getAssignedUserId()).child("Tasks").child(taskRef.getKey())
                    .child("Name")
                    .setValue(task.getName());

            myRef.child("Users").child(task.getAssignedUserId()).child("Tasks").child(taskRef.getKey())
                    .child("Sector")
                    .setValue(task.getGroup());

            myRef.child("Users").child(task.getAssignedUserId()).child("Tasks").child(taskRef.getKey())
                    .child("TimeCreated")
                    .setValue(task.getStart_date());

            myRef.child("Users").child(task.getAssignedUserId()).child("Tasks").child(taskRef.getKey())
                    .child("TaskPriority")
                    .setValue(task.getImportance());

            myRef.child("Users").child(task.getAssignedUserId()).child("Tasks").child(taskRef.getKey())
                    .child("AssignedUsers")
                    .setValue(task.getAssignedUsers());

            myRef.child("Users").child(task.getAssignedUserId()).child("Tasks").child(taskRef.getKey())
                    .child("EndDate")
                    .setValue(task.getEndDate());

            myRef.child("Users").child(task.getAssignedUserId()).child("Tasks").child(taskRef.getKey())
                    .child("TaskCreator")
                    .setValue(userID);

            myRef.child("Users").child(task.getAssignedUserId()).child("Tasks").child(taskRef.getKey())
                    .child("TaskRef")
                    .setValue(taskRef.getKey());

        }
        return setTaskInformation;
    }

}


