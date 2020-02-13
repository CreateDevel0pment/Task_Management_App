package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.codeacademyapp.R;
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
public class TaskDetailsFragment extends Fragment {
    private TaskInformation task;
    private TextView taskName, taskDesc, taskNote, taskDateCreated, taskPriority, taskEndDate;
    ImageView priorityIcon;
    Button taskCompletedBtn;
    String name, userId;
    private TaskViewModel taskViewModel;
    List<CompletedBy> completedByList, localCompletedByList;
    private DatabaseReference myRef;


    public TaskDetailsFragment(TaskInformation task) {
        this.task = task;
    }

    public TaskDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_task_details, container, false);

        taskViewModel = ViewModelProviders.of(TaskDetailsFragment.this).get(TaskViewModel.class);

        ChatViewModel groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        groupChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                userId = dataSnapshot.getKey();
            }
        });

        completedByList = new ArrayList<>();
        localCompletedByList = new ArrayList<>();

        taskPriority = rootView.findViewById(R.id.task_priority_details);
        taskName = rootView.findViewById(R.id.task_name_details);
        taskDesc = rootView.findViewById(R.id.task_desc_details);
        taskNote = rootView.findViewById(R.id.task_note_details);
        taskDateCreated = rootView.findViewById(R.id.task_dateCreated_details);
        taskEndDate = rootView.findViewById(R.id.task_endDate_details);
        priorityIcon = rootView.findViewById(R.id.priority_ic);
        taskCompletedBtn = rootView.findViewById(R.id.task_completed_btn);


        if (task.getTaskPriority().equals("High")) {
            priorityIcon.getDrawable().setTint((ContextCompat.getColor(getContext(), R.color.red)));
            taskPriority.setTextColor((ContextCompat.getColor(getContext(), R.color.red)));
        } else if (task.getTaskPriority().equals("Medium")) {
            priorityIcon.getDrawable().setTint((ContextCompat.getColor(getContext(), R.color.orange)));
            taskPriority.setTextColor((ContextCompat.getColor(getContext(), R.color.orange)));
        } else {

            priorityIcon.getDrawable().setTint((ContextCompat.getColor(getContext(), R.color.green)));
            taskPriority.setTextColor((ContextCompat.getColor(getContext(), R.color.green)));
        }

        String taskEndDateString = task.getEndDate();

        name = task.getName();

        taskName.setText(name);
        taskDesc.setText(task.getDescription());
        taskNote.setText(task.getNote());
        taskDateCreated.setText(task.getTimeCreated());
        taskPriority.setText(task.getTaskPriority());
        taskEndDate.setText(taskEndDateString);

        myRef = FirebaseDatabase.getInstance().getReference().child("Tasks").child(name);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    localCompletedByList = dataSnapshot.getValue(TaskInformation.class).getCompletedBy();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        taskCompletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CompletedBy completedByUser = new CompletedBy(userId);

                if(localCompletedByList==null){
                    completedByList.add(completedByUser);
                    task.setCompletedBy(completedByList);
                } else {
                    localCompletedByList.add(completedByUser);
                    task.setCompletedBy(localCompletedByList);
                }
                taskViewModel.addCompletedBy(task);
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }

}
