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

    public void saveMessageFromGroupChat(PublicMessage message) {
        chatRepository.saveMessageForGroupChat(message);
    }

    public LiveData<DataSnapshot> displayMessageToGroup(String curenUserGroup) {
        return chatRepository.displayMessageInUsersGroup(curenUserGroup);
    }

    public void saveMessageFromWallChat(PublicMessage message) {
        chatRepository.saveMessageForPublicChat(message);
    }

    public void saveDocFromWallChat(PublicMessage message) {
        chatRepository.saveDocForPublicChat(message);
    }

    public void saveDocFromGroupChat(PublicMessage message) {
        chatRepository.saveDocForGroupChat(message);
    }

    public LiveData<DataSnapshot> displayMessageToWall() {
        return chatRepository.displayMessageOnPublicWall();
    }

    public LiveData<DataSnapshot> displayMessageToPrivateChat(String message_reciever_id) {
        return chatRepository.displayMessageOnPrivateChat(message_reciever_id);
    }


    public LiveData<DataSnapshot> getchatRequest(String currentUserId) {
        return chatRepository.getChatRequest(currentUserId);
    }
}
