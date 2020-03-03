package com.example.codeacademyapp.ui.main.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.databinding.ActivityHomeSettingsBinding;

public class HomeSettings extends AppCompatActivity {


    HomeViewModel homeViewModel;
    ActivityHomeSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        homeViewModel= ViewModelProviders.of(this).get(HomeViewModel.class);
        binding.settingsOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.setHomePageUrl(binding.settingsWebText.getText().toString());
                finish();
            }
        });
    }
}
