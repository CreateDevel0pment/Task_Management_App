package com.example.codeacademyapp.ui.main.edit_find.edit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.sign_in_up.fragments.UserInformationViewModel;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    public static final int GALLERY_PICK = 1;

    CircleImageView profile_picture;
    TextView user_name;
    Spinner group, positionSpinner;
    Button edit_btn;
    Toolbar toolbar;
    String group_string, position_string;

    EditProfileViewModel editProfileViewModel;
    UserInformationViewModel userInformationViewModel;

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializedView();

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editProfileViewModel.updateUserInfo(position_string, group_string);

                Toast.makeText(EditProfileActivity.this, "Changes are updated", Toast.LENGTH_SHORT).show();
            }
        });

        getUserInformation();

        editProfileViewModel = ViewModelProviders.of(this).get(EditProfileViewModel.class);

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(EditProfileActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();


        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait, your profile image is updating");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                assert result != null;
                Uri resultUri = result.getUri();
                editProfileViewModel.getRefFromCloud(resultUri);

                loadingBar.dismiss();
            }
        }
    }

    private void getUserInformation() {

        userInformationViewModel=ViewModelProviders.of(this).get(UserInformationViewModel.class);
        userInformationViewModel.getUserInformation().observe(this,new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String username = dataSnapshot.child("Name").getValue().toString();

                    if (dataSnapshot.hasChild("image")) {

                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profile_picture);
                    }
                    user_name.setText(username);
                }
            }
        });
    }

    private void initializedView() {

        toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Edit Profile");

        profile_picture = findViewById(R.id.edit_profile_image);
        user_name = findViewById(R.id.edit_user_name);
        group = findViewById(R.id.edit_spinner_group);
        positionSpinner = findViewById(R.id.edit_spinner_role);
        edit_btn = findViewById(R.id.edit_button);

        loadingBar = new ProgressDialog(this);


        group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (group.getSelectedItem().equals("Android")) {
                    group_string = "Android";
                } else if (group.getSelectedItem().equals("Web Development")) {
                    group_string = "Web Development";
                } else if (group.getSelectedItem().equals("Ruby on rails")) {
                    group_string = "Ruby on rails";
                } else {
                    group_string = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (positionSpinner.getSelectedItem().equals("Manager")) {
                    position_string = "Manager";
                } else if (positionSpinner.getSelectedItem().equals("Staff")) {
                    position_string = "Staff";
                } else {
                    position_string = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
