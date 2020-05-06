package com.example.codeacademyapp.ui.main.info;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.databinding.ActivityHomeSettingsBinding;

public class SetInfo extends AppCompatActivity {

    InfoViewModel homeViewModel;
    ActivityHomeSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        homeViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);
        binding.settingsOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.setHomePageUrl(binding.settingsWebText.getText().toString());
                finish();
            }
        });
    }
}
