package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.TaskAdapter;
import com.example.codeacademyapp.data.model.AssignedUsers;
import com.example.codeacademyapp.data.model.CompletedBy;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.example.codeacademyapp.ui.main.sector.task.TaskViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalTaskFragment extends Fragment {

    private List<TaskInformation> tasks;
    private List<CompletedBy> completedByList;
    private DatabaseReference myRef;
    private TaskViewModel taskViewModel;

    private RecyclerView rvPersonalTasks;
    private String userId;
    private String name, group, note, timeCreated, taskPriority, description, endDate;
    private String id, completedCheck;


    private List<AssignedUsers> assignedUsersList;

    public PersonalTaskFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_personal_task, container, false);

        rvPersonalTasks = rootView.findViewById(R.id.personal_task_list_RV);

        ChatViewModel groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        groupChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                userId = dataSnapshot.getKey();
            }
        });


        tasks = new ArrayList<>();
        completedByList = new ArrayList<>();
        assignedUsersList = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference().child("Tasks");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot taskDataSnapshot : dataSnapshot.getChildren()) {

                    completedByList = taskDataSnapshot.getValue(TaskInformation.class).getCompletedBy();
                    assignedUsersList = taskDataSnapshot.getValue(TaskInformation.class).getAssignedUsers();
                    description = taskDataSnapshot.getValue(TaskInformation.class).getDescription();
                    group = taskDataSnapshot.getValue(TaskInformation.class).getSector();
                    note = taskDataSnapshot.getValue(TaskInformation.class).getNote();
                    name = taskDataSnapshot.getValue(TaskInformation.class).getName();
                    timeCreated = taskDataSnapshot.getValue(TaskInformation.class).getTimeCreated();
                    taskPriority = taskDataSnapshot.getValue(TaskInformation.class).getTaskPriority();
                    endDate = taskDataSnapshot.getValue(TaskInformation.class).getEndDate();
                    String state = taskDataSnapshot.getValue(TaskInformation.class).getState();

                    if (completedByList != null) {
                        for (int i = 0; i < completedByList.size(); i++) {
                            CompletedBy completedBy;
                            completedBy = completedByList.get(i);
                            id = completedBy.getUserId();
                        }
                        if (!id.equals(userId)) {
                            if (assignedUsersList != null) {
                                for (int i = 0; i < assignedUsersList.size(); i++) {
                                    AssignedUsers assignedUser;
                                    assignedUser = assignedUsersList.get(i);
                                    id = assignedUser.getUserId();

                                    if (id.equals(userId)) {
                                        TaskInformation task =
                                                new TaskInformation(name, description, note, group, timeCreated, taskPriority, endDate);
                                        tasks.add(task);
                                    }
                                }
                            }
                        }
                    } else {
                        if (assignedUsersList != null) {
                            for (int i = 0; i < assignedUsersList.size(); i++) {
                                AssignedUsers assignedUser;
                                assignedUser = assignedUsersList.get(i);
                                id = assignedUser.getUserId();

                                if (id.equals(userId)) {
                                    TaskInformation task =
                                            new TaskInformation(name, description, note, group, timeCreated, taskPriority, endDate);
                                    tasks.add(task);
                                }
                            }
                        }
                    }

                }

                String completedCheck = "";
                TaskAdapter taskAdapter = new TaskAdapter(getContext(), tasks, getFragmentManager(), userId, taskViewModel, completedCheck);
                rvPersonalTasks.setAdapter(taskAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvPersonalTasks.setLayoutManager(layoutManager);
        return rootView;

    }
}
