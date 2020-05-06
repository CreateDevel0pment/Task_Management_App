package com.example.codeacademyapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.codeacademyapp.ui.main.wall.WallFragment;
import com.example.codeacademyapp.ui.main.wall.MyContactsFragment;
import com.example.codeacademyapp.ui.main.wall.RequestsFragment;

import java.util.ArrayList;

public class WallPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<String> tab_PageTitle;

    public WallPagerAdapter(@NonNull FragmentManager fm, int behavior,ArrayList<String> tab_PageTitle) {
        super(fm, behavior);
        this.tab_PageTitle=tab_PageTitle;
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
        return tab_PageTitle.size();
    }



    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return Resources.getSystem().getString(R.string.tab_Wall);
//            case 1:
//                return Resources.getSystem().getString(R.string.tab_MyContacts);
//            case 2:
//                return Resources.getSystem().getString(R.string.tab_Requests);
//            default:
//                return null;
//        }
        return tab_PageTitle.get(position);
    }
}
