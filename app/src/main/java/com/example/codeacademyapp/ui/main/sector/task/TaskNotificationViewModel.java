package com.example.codeacademyapp.ui.main.sector.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.repository.CreateTaskRepository;
import com.google.android.gms.tasks.Task;

public class TaskNotificationViewModel extends AndroidViewModel {

    private CreateTaskRepository createTaskRepository;

    public TaskNotificationViewModel(@NonNull Application application) {
        super(application);

        createTaskRepository = new CreateTaskRepository();
    }

    public LiveData<Task> sendTaskNotification(String current_user_id, String receiver_user_id){
        return createTaskRepository.sendTaskNotification(current_user_id,receiver_user_id);
    }
}
