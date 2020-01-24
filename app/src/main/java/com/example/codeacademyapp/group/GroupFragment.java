package com.example.codeacademyapp.group;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.sign_in.BaseFragment;
import com.example.codeacademyapp.tasks.AddNewTaskFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends BaseFragment {


    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group, container, false);
        setTitle(R.string.my_group);

        FloatingActionButton addNewTaskButton = rootView.findViewById(R.id.add_new_task_floating_btn);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTaskFragment addNewTaskFragment = new AddNewTaskFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.main_container, addNewTaskFragment).commit();
            }
        });

        return rootView;



    }

}
