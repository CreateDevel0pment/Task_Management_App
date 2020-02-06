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
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.adapters.TaskAdapter;
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class ViewAllTaskFragment extends Fragment {

    private List<TaskInformation> tasks;
    private DatabaseReference myRef;

    private RecyclerView rvTasks;
    private String userSector;

    private View rootView;

    public ViewAllTaskFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_view_all_task, container, false);

        ChatViewModel groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        groupChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    userSector = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                }
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference().child("Tasks");

        rvTasks = rootView.findViewById(R.id.task_list_RV);
        tasks = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot taskDataSnapshot : dataSnapshot.getChildren()) {


                    String description = taskDataSnapshot.getValue(TaskInformation.class).getDescription();
                    String group = taskDataSnapshot.getValue(TaskInformation.class).getSector();
                    String note = taskDataSnapshot.getValue(TaskInformation.class).getNote();
                    String name = taskDataSnapshot.getValue(TaskInformation.class).getName();
                    String timeCreated = taskDataSnapshot.getValue(TaskInformation.class).getTimeCreated();
                    String taskPriority = taskDataSnapshot.getValue(TaskInformation.class).getTaskPriority();


                    if (group != null) {
                        if (group.equals(userSector)) {
                            TaskInformation task = new TaskInformation(name, description, note, group, timeCreated, taskPriority);
                            tasks.add(task);
                        }
                    }
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                rvTasks.setLayoutManager(layoutManager);
                TaskAdapter taskAdapter = new TaskAdapter(getContext(), tasks, getFragmentManager());
                rvTasks.setAdapter(taskAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return rootView;
    }

}


