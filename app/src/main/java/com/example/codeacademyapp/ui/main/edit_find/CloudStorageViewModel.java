package com.example.codeacademyapp.ui.main.edit_find;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.codeacademyapp.data.repository.CloudStorageRepository;

public class CloudStorageViewModel extends AndroidViewModel {

    private CloudStorageRepository cloudStorageRepository;

    public CloudStorageViewModel(@NonNull Application application) {
        super(application);
        cloudStorageRepository = new CloudStorageRepository();
    }

    public void getRefFromCloud(Uri file) {
        cloudStorageRepository.getRefFromCloud(file);
    }

}
