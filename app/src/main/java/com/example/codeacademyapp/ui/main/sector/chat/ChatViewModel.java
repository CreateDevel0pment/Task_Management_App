package com.example.codeacademyapp.ui.main.sector.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.repository.ChatRepository;
import com.google.firebase.database.DataSnapshot;

public class ChatViewModel extends AndroidViewModel {

    private ChatRepository chatRepository;

    public ChatViewModel(@NonNull Application application) {
        super(application);

        chatRepository = new ChatRepository();
    }

    public LiveData<DataSnapshot> getUserIngormations (){
        return chatRepository.getUserInformation();
    }

    public void setGroupNameToFirebase(String chatName) {
        chatRepository.setGroupNameToFirebase(chatName);
    }

    public void saveMessageFromGroupChat(String group_name, String currentUserName,
                                         String message, String currentDate,
                                         String currentTime) {
        chatRepository.saveMessageForGroupChat(group_name,currentUserName, message,currentDate,currentTime);
    }

    public LiveData<DataSnapshot> displayMessageToGroup(String curenUserGroup){
        return chatRepository.displayMessageInUsersGroup(curenUserGroup);
    }

    public LiveData<DataSnapshot> displayMessageToWall(){
        return chatRepository.displayMessageOnPublicWall();
    }

    public void saveMessageFromWallChat (String currentUserName,String userGroup,
                             String message, String currentDate,
                             String currentTime) {
        chatRepository.saveMessageForPublicChat(currentUserName,userGroup, message,currentDate,currentTime);
    }

//    public LiveData<Iterator<DataSnapshot>> getGroupNames(){
//        return chatRepository.getGroupNamesFromFirebase();
//    }
//
//    public LiveData<String> getReferencesForUsersGroup(){
//        return chatRepository.getReferencesForUsersGroup();
//    }
//
//    public void setWallChat (){
//        chatRepository.setWallChat();
//    }
}
