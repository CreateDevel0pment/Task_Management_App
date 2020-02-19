package com.example.codeacademyapp.adapters;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.Task;
import com.example.codeacademyapp.ui.main.sector.task.fragment.CompletedTasksFragment;
import com.example.codeacademyapp.ui.main.sector.task.fragment.PersonalTaskFragment;
import com.example.codeacademyapp.ui.main.sector.task.fragment.ViewAllTaskFragment;

import static com.example.codeacademyapp.R.drawable.group_chat;

public class NewTaskPagerAdapter extends FragmentPagerAdapter {

    public NewTaskPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        PersonalTaskFragment personalTask = new PersonalTaskFragment();
        ViewAllTaskFragment viewAllTaskFragment = new ViewAllTaskFragment();
        CompletedTasksFragment completedTasksFragment = new CompletedTasksFragment();

        if (position == 0) {
            return personalTask;
        } else if (position == 1){
            return viewAllTaskFragment;
        } else {
            return completedTasksFragment;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "My Tasks";
            case 1:
                return "Group Tasks";
            case 2:
                return "Completed";
            default:
                return null;

        }
    }
}
