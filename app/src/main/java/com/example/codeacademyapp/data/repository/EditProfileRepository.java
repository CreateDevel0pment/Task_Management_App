package com.example.codeacademyapp.data.repository;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class EditProfileRepository {

    private FirebaseAuth auth;
    private DatabaseReference roothRef,userRef,messageRef;


    public EditProfileRepository() {
        auth=FirebaseAuth.getInstance();
    }

    public void getRefFromCloud(Uri file){

        StorageReference userProfileImagesRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
        roothRef= FirebaseDatabase.getInstance().getReference();
        final String currentUserId= Objects.requireNonNull(auth.getCurrentUser()).getUid();

        StorageReference storagePath= userProfileImagesRef.child(currentUserId + ".jpg");
        storagePath.putFile(file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){

                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            roothRef.child("Users").child(currentUserId).child("image").setValue(uri.toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });

                        }
                    });
                }
            }
        });
    }

    public void updateUserInformation(String position_string,String group_string) {
        String currentUserId =auth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        if (!position_string.equals("")) {
            userRef.child("Position").setValue(position_string).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }
        if (!group_string.equals("")) {
            userRef.child("Sector").setValue(group_string).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }
    }
}
