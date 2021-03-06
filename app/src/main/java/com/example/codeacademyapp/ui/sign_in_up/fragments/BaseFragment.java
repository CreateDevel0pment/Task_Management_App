package com.example.codeacademyapp.ui.sign_in_up.fragments;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public abstract class BaseFragment extends Fragment {

    protected void setTitle(@StringRes int stringId) {
        setTitle(getString(stringId));
    }

    protected void setTitle(String title) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle(title);
        }
    }
}
