package com.example.codeacademyapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.codeacademyapp.MainActivity;
import com.example.codeacademyapp.ui.SignInFragment;
import com.example.codeacademyapp.ui.SignUpFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    public TabPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        SignInFragment signInFragment = new SignInFragment();
        SignUpFragment signUpFragment = new SignUpFragment();

        if(position == 0){
            return signInFragment;
        } else {
            return signUpFragment;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sign In";
            case 1:
                return "Sign Up";
            default:
                return null;

        }        }
}
