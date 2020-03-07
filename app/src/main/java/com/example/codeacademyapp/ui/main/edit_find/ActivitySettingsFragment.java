package com.example.codeacademyapp.ui.main.edit_find;


import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.codeacademyapp.R;

public class ActivitySettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.shared_pref_theme_color_switch);
    }
}
