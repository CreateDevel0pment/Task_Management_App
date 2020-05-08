package com.example.codeacademyapp.ui.main.sector.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.model.Task;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.data.repository.CreateTaskRepository;
import com.google.firebase.database.DataSnapshot;

public class TaskViewModel extends AndroidViewModel {

    private CreateTaskRepository createTaskRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        createTaskRepository = new CreateTaskRepository();

    }

    public void createGroupTask(Task task){
        LiveData<Task> createGroupTaskLiveData = createTaskRepository.createGroupTask(task);
    }

    public void createAssignedTask(Task task){
        LiveData<Task> createAssignedTaskLiveData = createTaskRepository.createAssignedTask(task);
    }


    public LiveData<DataSnapshot> checkForDoc(String userGroup) {
        return createTaskRepository.uploadDocChecker(userGroup);
    }


    public void addCompletedBy(TaskInformation taskInformation){
        LiveData<TaskInformation> createNewTaskInformationLiveData = createTaskRepository.addCompletedByOnTask(taskInformation);
    }
}
