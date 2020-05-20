package com.example.codeacademyapp.ui.main.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.sector.task.TaskNotificationViewModel;
import com.example.codeacademyapp.ui.main.sector.task.fragment.AddNewTaskFragment;
import com.example.codeacademyapp.ui.sign_in_up.fragments.UserInformationViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

public class SchedulerActivity extends AppCompatActivity {

    Toolbar toolbar;
    FragmentManager fm;
    UserInformationViewModel userInformationViewModel;
    String userSector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        toolbar = findViewById(R.id.toolbar_scheduler);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Scheduler");

        userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        userInformationViewModel.getUserInformation().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userSector = Objects.requireNonNull(dataSnapshot.child("Position").getValue()).toString();

                    GetScheduleFragment getScheduleFragment = new GetScheduleFragment();
                    CreateScheduleFragment createScheduleFragment = new CreateScheduleFragment();
                    fm = getSupportFragmentManager();

                    if (userSector!=null){
                        if (!userSector.equals("Manager")){
                            fm.beginTransaction()
                                    .replace(R.id.scheduler_container, getScheduleFragment)
                                    .commit();
                        } else {
                            fm.beginTransaction()
                                    .replace(R.id.scheduler_container, createScheduleFragment)
                                    .commit();
                        }
                    }
                }
            }
        });









    }
}
