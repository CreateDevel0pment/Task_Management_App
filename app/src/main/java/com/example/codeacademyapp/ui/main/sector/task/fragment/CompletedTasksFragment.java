package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedTasksFragment extends Fragment {


    private List<TaskInformation> tasks;
    private DatabaseReference myRef;
    private List<AssignedUsers> assignedUsersList;
    private List<CompletedBy> completedByList;
    private TaskAdapter taskAdapter;
    TaskViewModel taskViewModel;

    private RecyclerView rvTasks;
    private String userSector, userId, id;

    private View rootView;

    public CompletedTasksFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_completed_tasks, container, false);

        ChatViewModel groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        groupChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    userSector = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                }

                userId = dataSnapshot.getKey();


                myRef = FirebaseDatabase.getInstance().getReference().child("Tasks").child("CompletedTasks").child(userSector);

                assignedUsersList = new ArrayList<>();
                completedByList = new ArrayList<>();
                rvTasks = rootView.findViewById(R.id.tasks_completed_list_RV);
                tasks = new ArrayList<>();

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tasks.clear();
                        for (DataSnapshot taskDataSnapshot : dataSnapshot.getChildren()) {

                            assignedUsersList = taskDataSnapshot.getValue(TaskInformation.class).getAssignedUsers();
                            completedByList = taskDataSnapshot.getValue(TaskInformation.class).getCompletedBy();
                            String description = taskDataSnapshot.getValue(TaskInformation.class).getDescription();
                            String group = taskDataSnapshot.getValue(TaskInformation.class).getSector();
                            String name = taskDataSnapshot.getValue(TaskInformation.class).getName();
                            String timeCreated = taskDataSnapshot.getValue(TaskInformation.class).getTimeCreated();
                            String taskPriority = taskDataSnapshot.getValue(TaskInformation.class).getTaskPriority();
                            String endDate = taskDataSnapshot.getValue(TaskInformation.class).getEndDate();
                            String taskRef = taskDataSnapshot.getValue(TaskInformation.class).getTaskRef();

                            TaskInformation task = new TaskInformation(name, description,
                                     group, timeCreated, taskPriority, endDate, taskRef);
                            tasks.add(task);

//                    if (completedByList != null) {
//                        for (int i = 0; i < completedByList.size(); i++) {
//                            CompletedBy completedBy;
//                            completedBy = completedByList.get(i);
//                            id = completedBy.getUserId();
//                            if (id.equals(userId)) {
//                                if (assignedUsersList == null) {
//                                    if (group != null) {
//                                        if (group.equals(userSector)) {
//
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//
//                    }
                        }

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        rvTasks.setLayoutManager(layoutManager);
                        String completedCheck = "completeGONE";
                        taskAdapter = new TaskAdapter(getContext(), tasks, getFragmentManager(), userId, taskViewModel, completedCheck);
                        rvTasks.setAdapter(taskAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return rootView;
    }

}
