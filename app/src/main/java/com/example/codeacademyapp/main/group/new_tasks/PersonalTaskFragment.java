package com.example.codeacademyapp.main.group.new_tasks;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codeacademyapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalTaskFragment extends Fragment {


    public PersonalTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_task, container, false);
    }

}
