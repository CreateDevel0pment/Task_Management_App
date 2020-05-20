package com.example.codeacademyapp.ui.main.scheduler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codeacademyapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateScheduleFragment extends Fragment {

    public CreateScheduleFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_schedule, container, false);

        WorkdayViewModel workdayViewModel = ViewModelProviders.of(CreateScheduleFragment.this).get(WorkdayViewModel.class);

        RecyclerView rvWorkDays = view.findViewById(R.id.workDaysRV);


        Date date = new Date();

        final List<Workday> workdays;
        workdays = new ArrayList<>(7);

        workdays.add(new Workday("(Today) ", date));
        workdays.add(new Workday("(Tomorrow) ", addDays(date, 1)));
        workdays.add(new Workday(addDays(date, 2)));
        workdays.add(new Workday(addDays(date, 3)));
        workdays.add(new Workday(addDays(date, 4)));
        workdays.add(new Workday(addDays(date, 5)));
        workdays.add(new Workday(addDays(date, 6)));

        WorkDayCreatorAdapter adapter = new WorkDayCreatorAdapter(workdays, getContext(), workdayViewModel, this, getFragmentManager());
        rvWorkDays.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvWorkDays.setLayoutManager(layoutManager);



        return view;
    }

    private static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
