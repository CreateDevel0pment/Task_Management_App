package com.example.codeacademyapp.ui.main.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.codeacademyapp.R;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.schedule_container,new ScheduleFragment())
                .commit();
    }

    public void addToSchedule(View view) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.schedule_container,new AddToScheduleFragment())
                .commit();
    }
}
