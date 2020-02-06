package com.example.codeacademyapp.ui.main.edit_find.edit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.example.codeacademyapp.ui.main.edit_find.CloudStorageViewModel;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    public static final int GALLERY_PICK=1;

    CircleImageView profile_picture;
    TextView user_name;
    Spinner group, position;
    Button edit_btn;

    ChatViewModel viewModel;
    CloudStorageViewModel cloudStorageViewModel;

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initializedView();

        userInformationViewModel();

        cloudStorageViewModel=ViewModelProviders.of(this).get(CloudStorageViewModel.class);

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(EditProfileActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null){

            Uri imageUri=data.getData();


        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){

                loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait, your profile image is updating");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Uri resultUri=result.getUri();
                cloudStorageViewModel.getRefFromCloud(resultUri);

                loadingBar.dismiss();
            }
        }
    }

    private void userInformationViewModel(){
        viewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        viewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String username= dataSnapshot.child("Name").getValue().toString();

                    if(dataSnapshot.hasChild("image")) {

                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profile_picture);
                    }
                    user_name.setText(username);
                }
            }
        });
    }

    private void initializedView() {
        profile_picture = findViewById(R.id.edit_profile_image);
        user_name = findViewById(R.id.edit_user_name);
        group = findViewById(R.id.edit_spinner_group);
        position = findViewById(R.id.edit_spinner_role);
        edit_btn = findViewById(R.id.edit_button);
        loadingBar=new ProgressDialog(this);
    }
}
