package com.example.codeacademyapp.ui.main.sector.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.model.PublicMessage;
import com.example.codeacademyapp.data.repository.ChatRepository;
import com.google.firebase.database.DataSnapshot;

public class ChatViewModel extends AndroidViewModel {

    private ChatRepository chatRepository;

    public ChatViewModel(@NonNull Application application) {
        super(application);

        chatRepository = new ChatRepository();
    }

    public void setGroupNameToFirebase(String chatName) {
        chatRepository.setGroupNameToFirebase(chatName);
    }

    public void saveMessage(PublicMessage message,String chat_name,String group_name) {
        chatRepository.saveMessage(message,chat_name,group_name);
    }

    public void saveDocumentFile(PublicMessage message,String chat_name,String group_name) {
        chatRepository.saveDocumentFile(message,chat_name,group_name);
    }

    public LiveData<DataSnapshot> displayMessage(String chat_name, String user_group) {
        return chatRepository.displayMessage(chat_name, user_group);
    }

    public LiveData<DataSnapshot> displayMessageToPrivateChat(String message_reciever_id) {
        return chatRepository.displayMessageOnPrivateChat(message_reciever_id);
    }
}
