package com.example.codeacademyapp.ui.main.edit_find.find_friends;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.repository.ChatRequestRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class ChatRequestViewModel extends AndroidViewModel {

   private ChatRequestRepository chatRequestRepository;

    public ChatRequestViewModel(@NonNull Application application) {
        super(application);

        chatRequestRepository= new ChatRequestRepository();
    }

    public LiveData<Task> getChatRequest (String current_user_id, String receiver_user_id){
        return chatRequestRepository.sendChatRequest(current_user_id,receiver_user_id);
    }

    public LiveData<Task> cancelChatRequest (String current_user_id, String receiver_user_id){
        return chatRequestRepository.cancelChatRequest(current_user_id,receiver_user_id);
    }

    public LiveData<Task> acceptChatRequest (String current_user_id, String receiver_user_id){
        return chatRequestRepository.acceptChatRequest(current_user_id,receiver_user_id);
    }

    public LiveData<Task> removeFromMyContacts (String current_user_id, String receiver_user_id){
        return chatRequestRepository.removeFromMyContacts(current_user_id,receiver_user_id);
    }

    public LiveData<DataSnapshot> setValueForRemove (String current_user_id, String receiver_user_id){
        return chatRequestRepository.removeContacts(current_user_id,receiver_user_id);
    }

    public LiveData<DataSnapshot> getChatRequestData(String currentUserId) {
        return chatRequestRepository.getChatRequest(currentUserId);
    }
}
