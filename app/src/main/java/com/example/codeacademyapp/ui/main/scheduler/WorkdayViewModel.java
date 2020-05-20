package com.example.codeacademyapp.ui.main.scheduler;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;

public class WorkdayViewModel extends AndroidViewModel {

    private WorkdayRepository workdayRepository;

    public WorkdayViewModel(@NonNull Application application) {
        super(application);

        workdayRepository = new WorkdayRepository();

    }

    public void createWorkday(Workday workday){
        LiveData<Workday> createWorkdayLiveData = workdayRepository.addWorkdaysForUser(workday);
    }


    public LiveData<DataSnapshot> checkWorkDay (Workday workday){
        return workdayRepository.getWorkDayForUser(workday);
    }

    public  LiveData<DataSnapshot> getSchedule(Workday workday){
        return workdayRepository.getWorkdaySchedule(workday);
    }

}
