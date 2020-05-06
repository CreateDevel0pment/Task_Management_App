package com.example.codeacademyapp.ui.main.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.WallPagerAdapter;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WallTabFragment extends BaseFragment {

    private ViewPager viewPager;
    private ArrayList<String> tab_PageTitle;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            return view;
        }
        view = inflater.inflate(R.layout.fragment_wall_pager, container, false);

        tab_PageTitle= new ArrayList<>();
        tab_PageTitle.add(getString(R.string.tab_Wall));
        tab_PageTitle.add(getString(R.string.tab_MyContacts));
        tab_PageTitle.add(getString(R.string.tab_Requests));

        TabLayout tabLayout = view.findViewById(R.id.tab_layout_public_wall);
        viewPager = view.findViewById(R.id.view_pager_public_wall);
        WallPagerAdapter adapter = new WallPagerAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tab_PageTitle);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return view;
    }
}
