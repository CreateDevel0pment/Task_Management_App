package com.example.codeacademyapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.data.model.Task;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateTaskRepository {

    private MutableLiveData<Task> setTaskInformation;
    private MutableLiveData<TaskInformation> taskInformationMutableLiveData;
    private FirebaseDatabase firebaseDatabase, notificationRef;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef, notifRef;
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

        myRef.child("Users").child(userID).child("CompletedTasks").child(taskRef.getKey())
                .child("CompletedBy")
                .setValue(taskInformation.getCompletedBy());
        myRef.child("Users").child(userID).child("CompletedTasks").child(taskRef.getKey())
                .child("Description")
                .setValue(taskInformation.getDescription());
        myRef.child("Users").child(userID).child("CompletedTasks").child(taskRef.getKey())
                .child("Name")
                .setValue(taskInformation.getName());
        myRef.child("Users").child(userID).child("CompletedTasks").child(taskRef.getKey())
                .child("TimeCreated")
                .setValue(taskInformation.getTimeCreated());
        myRef.child("Users").child(userID).child("CompletedTasks").child(taskRef.getKey())
                .child("TaskPriority")
                .setValue(taskInformation.getTaskPriority());
        myRef.child("Users").child(userID).child("CompletedTasks").child(taskRef.getKey())
                .child("EndDate")
                .setValue(taskInformation.getEndDate());
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

    public MutableLiveData<com.google.android.gms.tasks.Task> sendTaskNotification(final String current_user_id, final String receiver_user_id){

        final MutableLiveData<com.google.android.gms.tasks.Task> getTaskRequest= new MutableLiveData<>();

        notificationRef = FirebaseDatabase.getInstance();
        notifRef = notificationRef.getReference();

        HashMap<String, String> taskNotificationMap = new HashMap<>();
        taskNotificationMap.put("from", current_user_id);
        taskNotificationMap.put("type", "notification");

        notifRef.child("TaskNotifications").child(receiver_user_id).push()
                .setValue(taskNotificationMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        if(task.isSuccessful()){
                            getTaskRequest.setValue(task);
                        }
                    }
                });

        return getTaskRequest;
    }

}


