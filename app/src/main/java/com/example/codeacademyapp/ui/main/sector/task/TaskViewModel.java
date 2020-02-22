package com.example.codeacademyapp.ui.main.sector.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.model.Task;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.data.repository.CreateTaskRepository;

public class TaskViewModel extends AndroidViewModel {

    private CreateTaskRepository createTaskRepository;
    private LiveData<Task> createGroupTaskLiveData;
    private LiveData<Task> createAssignedTaskLiveData;
    private LiveData<TaskInformation> createNewTaskInformationLiveData;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        createTaskRepository = new CreateTaskRepository();

    }

    public void createGroupTask(Task task){
        createGroupTaskLiveData = createTaskRepository.createGroupTask(task);
    }

    public void createAssignedTask(Task task){
        createAssignedTaskLiveData = createTaskRepository.createAssignedTask(task);
    }


    public void addCompletedBy(TaskInformation taskInformation){
        createNewTaskInformationLiveData = createTaskRepository.addCompletedByOnTask(taskInformation);
    }


    public LiveData<Task> getCreateGroupTaskLiveData() {
        return createGroupTaskLiveData;
    }

    public LiveData<TaskInformation> getCreateNewTaskInformationLiveData(){
        return createNewTaskInformationLiveData;
    }

    public LiveData<Task> getCreateAssignedTaskLiveData() {
        return createAssignedTaskLiveData;
    }
}
