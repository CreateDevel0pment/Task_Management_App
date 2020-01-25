package com.example.codeacademyapp.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.codeacademyapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Button update_account_btn;
    private EditText user_name, user_status;
    private CircleImageView userProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeFields();
    }

    private void initializeFields() {

        update_account_btn = findViewById(R.id.update_settings_button);
        user_name=findViewById(R.id.set_user_name);
        user_status=findViewById(R.id.set_profile_status);
        userProfileImage=findViewById(R.id.set_circular_image);
    }
}
