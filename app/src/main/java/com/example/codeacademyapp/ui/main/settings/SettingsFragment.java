package com.example.codeacademyapp.ui.main.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.codeacademyapp.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

          setPreferencesFromResource(R.xml.general_settings,rootKey);

        SwitchPreference languageSettings=getPreferenceManager().findPreference("key_language");

        assert languageSettings != null;
        languageSettings.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                Objects.requireNonNull(getActivity()).recreate();
                return true;
            }
        });
    }


}
