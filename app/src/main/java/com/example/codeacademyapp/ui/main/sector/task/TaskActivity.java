package com.example.codeacademyapp.ui.main.sector.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.sector.task.fragment.TaskTabsFragment;

public class TaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Toolbar toolbar = findViewById(R.id.toolbar_tasks_tab_fragment);
        toolbar.setTitle("Back to group chat");
        toolbar.setTitleTextColor((ContextCompat.getColor(this, R.color.white)));

        toolbar.setNavigationIcon(R.drawable.ic_back_button_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TaskTabsFragment taskTabsFragment = new TaskTabsFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.task_fragments_container, taskTabsFragment)
                .commit();
    }
}