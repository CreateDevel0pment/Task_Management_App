package com.example.codeacademyapp.ui.main.scheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.example.codeacademyapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetScheduleFragment extends Fragment {
    private DateRangeCalendarView datePicker;
    private int endDateDayInt, endDateMonthInt, endDateYearInt, startDateDayInt, startDateMonthInt, startDateYearInt;
    private String startDateString, endDateString;
    private Date sDate, eDate;
    WorkDayAdapter getWorkDayAdapter;
    RecyclerView rvShifts;
    WorkdayViewModel workdayViewModel;

    public GetScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_schedule, container, false);
        workdayViewModel = ViewModelProviders.of(GetScheduleFragment.this).get(WorkdayViewModel.class);


        rvShifts = view.findViewById(R.id.employee_rv_shifts_per_day);

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

        getWorkDayAdapter = new WorkDayAdapter(workdays, getContext(), workdayViewModel, this);
        rvShifts.setAdapter(getWorkDayAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvShifts.setLayoutManager(layoutManager);


        return view;
    }

    private static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
