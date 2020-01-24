package com.example.codeacademyapp.ui.home;


import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codeacademyapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setTitle(R.string.home_fragment_title);

        return view;
    }

    protected void setTitle(@StringRes int stringId) {
        setTitle(getString(stringId));
    }

    protected void setTitle(String title) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle(title);
        }
}}
