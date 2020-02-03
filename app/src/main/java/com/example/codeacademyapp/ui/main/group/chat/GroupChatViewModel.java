package com.example.codeacademyapp.ui.main.group.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.data.repository.GroupChatRepository;
import com.google.firebase.database.DataSnapshot;

public class GroupChatViewModel extends AndroidViewModel {

    private GroupChatRepository chatRepository;

    public GroupChatViewModel(@NonNull Application application) {
        super(application);

        chatRepository = new GroupChatRepository();
    }

    public LiveData<DataSnapshot> getUserIngormations (){
        return chatRepository.getUserInformation();
    }

    public void setGroupNameToFirebase(String chatName) {
        chatRepository.setGroupNameToFirebase(chatName);
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
