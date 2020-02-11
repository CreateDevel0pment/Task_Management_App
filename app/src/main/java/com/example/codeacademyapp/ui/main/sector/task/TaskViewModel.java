package com.example.codeacademyapp.ui.main.sector.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.model.CompletedBy;
import com.example.codeacademyapp.data.model.Task;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.data.repository.CreateTaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private CreateTaskRepository createTaskRepository;
    private LiveData<Task> createNewTaskLiveData;
    private LiveData<TaskInformation> createNewTaskInformationLiveData;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        createTaskRepository = new CreateTaskRepository();

    }

    public void createNewTask(Task task){
        createNewTaskLiveData = createTaskRepository.createNewTask(task);
    }

    public void addCompletedBy(TaskInformation taskInformation){
        createNewTaskInformationLiveData = createTaskRepository.addCompletedByOnTask(taskInformation);
    }

    public LiveData<Task> getCreateNewTaskLiveData() {
        return createNewTaskLiveData;
    }
}
