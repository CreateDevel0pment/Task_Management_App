package com.example.codeacademyapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.codeacademyapp.ui.main.wall.WallFragment;
import com.example.codeacademyapp.ui.main.wall.MyContactsFragment;
import com.example.codeacademyapp.ui.main.wall.RequestsFragment;

public class WallPagerAdapter extends FragmentPagerAdapter {

    public WallPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        WallFragment wallFragment = new WallFragment();
        MyContactsFragment myContactsFragment = new MyContactsFragment();
        RequestsFragment requestsFragment = new RequestsFragment();

        if (position == 0) {
            return wallFragment;
        } else if (position == 1) {
            return myContactsFragment;
        }
        else {
            return requestsFragment;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Wall";
            case 1:
                return "My Contacts";
            case 2:
                return "Requests";
            default:
                return null;
        }
    }
}
