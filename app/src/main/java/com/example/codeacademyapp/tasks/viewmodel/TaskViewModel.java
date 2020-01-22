package com.example.codeacademyapp.tasks.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.tasks.model.Task;
import com.example.codeacademyapp.tasks.repository.CreateTaskRepository;

public class TaskViewModel extends AndroidViewModel {

    private CreateTaskRepository createTaskRepository;
    private LiveData<Task> createNewTaskLiveData;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        createTaskRepository = new CreateTaskRepository();

    }

    public void createNewTask(Task task){
        createNewTaskLiveData = createTaskRepository.createNewTask(task);
    }

    public LiveData<Task> getCreateNewTaskLiveData() {
        return createNewTaskLiveData;
    }
}
