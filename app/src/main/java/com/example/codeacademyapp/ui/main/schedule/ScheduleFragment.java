package com.example.codeacademyapp.ui.main.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.WeekDaysAdapter;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private View view;
    private TextView nameUser, positionUser;
    private RecyclerView scheduleDayList;
    private String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private WeekDaysAdapter weekDaysAdapter;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        initializedViewItems(view);

        weekDaysAdapter= new WeekDaysAdapter(days);
        scheduleDayList.setAdapter(weekDaysAdapter);

        return view;
    }

    private void initializedViewItems(View view){
        nameUser=view.findViewById(R.id.user_schedule_name);
        positionUser=view.findViewById(R.id.user_schedule_position);
        scheduleDayList=view.findViewById(R.id.schedule_daysList);
        scheduleDayList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
