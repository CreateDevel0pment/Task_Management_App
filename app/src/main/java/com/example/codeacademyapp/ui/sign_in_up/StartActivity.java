package com.example.codeacademyapp.ui.sign_in_up;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.sign_in_up.fragments.FragmentStartMain;

public class StartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FragmentStartMain fragmentStartMain = new FragmentStartMain();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.start_activity_main_container, fragmentStartMain)
                .commit();
    }
}