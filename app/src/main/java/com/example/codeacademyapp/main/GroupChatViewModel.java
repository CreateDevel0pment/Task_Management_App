package com.example.codeacademyapp.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeacademyapp.main.repository.GroupChatRepository;
import com.google.firebase.database.DataSnapshot;

import java.util.Iterator;

public class GroupChatViewModel extends AndroidViewModel {

    private GroupChatRepository chatRepository;

    public GroupChatViewModel(@NonNull Application application) {
        super(application);

        chatRepository = new GroupChatRepository();
    }

    public void setGroupNameOnFirebase(String chatName) {
        chatRepository.setGroupNameToFirebase(chatName);
    }

    public LiveData<Iterator<DataSnapshot>> getGroupNames(){
        return chatRepository.getGroupNamesFromFirebase();
    }
}
