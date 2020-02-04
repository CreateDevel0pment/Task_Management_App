package com.example.codeacademyapp.ui.main.group.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.NewTaskPagerAdapter;
import com.example.codeacademyapp.ui.main.group.task.fragment.AddNewTaskFragment;
import com.example.codeacademyapp.ui.main.group.task.fragment.TaskTabsFragment;
import com.google.android.material.tabs.TabLayout;

public class TaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        TaskTabsFragment taskTabsFragment = new TaskTabsFragment(getSupportFragmentManager());
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.task_fragments_container, taskTabsFragment)
                .commit();

    }
}