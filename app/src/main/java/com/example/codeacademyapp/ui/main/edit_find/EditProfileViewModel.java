package com.example.codeacademyapp.ui.main.edit_find;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.codeacademyapp.data.repository.EditProfileRepository;

public class EditProfileViewModel extends AndroidViewModel {

    private EditProfileRepository editProfileRepository;

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        editProfileRepository = new EditProfileRepository();
    }

    public void getRefFromCloud(Uri file) {
        editProfileRepository.getRefFromCloud(file);
    }

    public void updateUserInfo (String position_string,String group_string ){

        editProfileRepository.updateUserInformation(position_string,group_string);
    }

}
