package com.example.codeacademyapp.main.edit_profile;

import android.net.Uri;

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

public class CloudStorageRepository {

    private FirebaseAuth auth;
    private DatabaseReference roothRef;


    public CloudStorageRepository() {
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


//                    String image= task.getResult().getStorage().getDownloadUrl().toString();
//
//                    roothRef.child("Users").child(currentUserId).child("image").setValue(image)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                }
//                            });

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
                }else {

                    String error= Objects.requireNonNull(task.getException()).getMessage();
                }
            }
        });
    }
}
