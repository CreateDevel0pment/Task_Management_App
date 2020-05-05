package com.example.codeacademyapp.ui.main.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.codeacademyapp.R;

import java.util.Locale;

public class SettingsActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_actvity);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settingsContainer, new SettingsFragment())
                .commit();

        setLanguageSettingsPreferences();

        PreferenceManager.setDefaultValues(this,R.xml.general_settings,false);
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);

        String prefValue=preferences.getString("key_language", "default");


    }

    private void setLanguageSettingsPreferences(){
        Resources res = getBaseContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        boolean lang_boolean= PreferenceManager.getDefaultSharedPreferences(this).getBoolean("key_language", false);
        String lang=(lang_boolean)?"mk":"en";
        conf.setLocale(new Locale(lang.toLowerCase()));
        res.updateConfiguration(conf, dm);
    }
}
