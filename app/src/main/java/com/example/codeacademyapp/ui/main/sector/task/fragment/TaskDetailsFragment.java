package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.TaskInformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailsFragment extends Fragment {
    private TaskInformation task;
    private TextView taskName, taskDesc, taskNote, taskDateCreated, taskPriority, taskEndDate;
    RoundCornerProgressBar priorityProgressBar;


    public TaskDetailsFragment(TaskInformation task) {
        this.task = task;
    }

    public TaskDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_details, container, false);



        taskPriority = rootView.findViewById(R.id.task_priority_details);
        taskName = rootView.findViewById(R.id.task_name_details);
        taskDesc = rootView.findViewById(R.id.task_desc_details);
        taskNote = rootView.findViewById(R.id.task_note_details);
        taskDateCreated = rootView.findViewById(R.id.task_dateCreated_details);
        taskEndDate = rootView.findViewById(R.id.task_endDate_details);

        priorityProgressBar = rootView.findViewById(R.id.task_priority_progress_bar);
        priorityProgressBar.setMax(3);

        if (task.getTaskPriority().equals("High")) {
            priorityProgressBar.setProgress(3);
            priorityProgressBar.setProgressColor((ContextCompat.getColor(getContext(), R.color.red)));
            taskPriority.setTextColor((ContextCompat.getColor(getContext(), R.color.red)));
        } else if (task.getTaskPriority().equals("Medium")) {
            priorityProgressBar.setProgress(2);
            priorityProgressBar.setProgressColor((ContextCompat.getColor(getContext(), R.color.orange)));
            taskPriority.setTextColor((ContextCompat.getColor(getContext(), R.color.orange)));
        } else {
            priorityProgressBar.setProgress(1);
            priorityProgressBar.setProgressColor((ContextCompat.getColor(getContext(), R.color.green)));
            taskPriority.setTextColor((ContextCompat.getColor(getContext(), R.color.green)));
        }

        String taskEndDateString = task.getEndDate();
        String name = task.getName();

        taskName.setText(task.getName());
        taskDesc.setText(task.getDescription());
        taskNote.setText(task.getNote());
        taskDateCreated.setText(task.getTimeCreated());
        taskPriority.setText(task.getTaskPriority());
        taskEndDate.setText(taskEndDateString);

        return rootView;
    }

}
