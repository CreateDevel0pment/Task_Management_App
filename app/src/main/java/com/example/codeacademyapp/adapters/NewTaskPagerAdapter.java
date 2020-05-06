package com.example.codeacademyapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.codeacademyapp.ui.main.sector.task.fragment.CompletedTasksFragment;
import com.example.codeacademyapp.ui.main.sector.task.fragment.PersonalTaskFragment;
import com.example.codeacademyapp.ui.main.sector.task.fragment.ViewAllTaskFragment;

import java.util.ArrayList;

public class NewTaskPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> tab_PageTitle;

    public NewTaskPagerAdapter(@NonNull FragmentManager fm, int behavior,ArrayList<String> tab_PageTitle ) {
        super(fm, behavior);
        this.tab_PageTitle=tab_PageTitle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        PersonalTaskFragment personalTask = new PersonalTaskFragment();
        ViewAllTaskFragment viewAllTaskFragment = new ViewAllTaskFragment();
        CompletedTasksFragment completedTasksFragment = new CompletedTasksFragment();

        if (position == 0) {
            return personalTask;
        } else if (position == 1) {
            return viewAllTaskFragment;
        } else {
            return completedTasksFragment;
        }

    }

    @Override
    public int getCount() {
        return tab_PageTitle.size();
    }

    public CharSequence getPageTitle(int position) {

        return tab_PageTitle.get(position);

    }

}
