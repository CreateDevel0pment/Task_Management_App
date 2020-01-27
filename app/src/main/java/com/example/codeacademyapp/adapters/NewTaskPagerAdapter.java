package com.example.codeacademyapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.codeacademyapp.main.group.new_tasks.PersonalTaskFragment;
import com.example.codeacademyapp.main.group.new_tasks.ViewAllTaskFragment;

public class NewTaskPagerAdapter extends FragmentPagerAdapter {

    public NewTaskPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        PersonalTaskFragment personalTask = new PersonalTaskFragment();
        ViewAllTaskFragment viewAllTaskFragment = new ViewAllTaskFragment();

        if(position == 0){
            return personalTask;
        } else {
            return viewAllTaskFragment;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Task";
            case 1:
                return "All Tasks";
            default:
                return null;

        }        }
}
