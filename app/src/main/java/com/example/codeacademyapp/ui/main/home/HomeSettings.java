package com.example.codeacademyapp.ui.main.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.codeacademyapp.R;

public class HomeSettings extends AppCompatActivity {

    Button settings_btn;
    EditText web_edit;
    HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_settings);

        homeViewModel= ViewModelProviders.of(this).get(HomeViewModel.class);

        settings_btn=findViewById(R.id.settings_ok_btn);
        web_edit=findViewById(R.id.settings_web_text);

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeViewModel.setHomePageUrl(web_edit.getText().toString());
                finish();
            }
        });
    }
}
