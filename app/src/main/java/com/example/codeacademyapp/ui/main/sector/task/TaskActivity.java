package com.example.codeacademyapp.ui.main.sector.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.sector.task.fragment.TaskTabsFragment;

public class TaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        TaskTabsFragment taskTabsFragment = new TaskTabsFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.task_fragments_container, taskTabsFragment)
                .commit();

    }
}