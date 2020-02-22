package com.example.codeacademyapp.ui.sign_in_up.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.TabPagerAdapter;
import com.example.codeacademyapp.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStartMain extends Fragment {

    private TabPagerAdapter adapter;

    public FragmentStartMain() {
    }

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_fragment_start_main, container, false);

        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout);
        final ViewPager viewPager = rootView.findViewById(R.id.view_pager);
        if (getFragmentManager() != null) {
            adapter = new TabPagerAdapter(getFragmentManager(),
                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }
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
        int[] landing_pictures = new int[]{
                R.drawable.comunicate_plava,
                R.drawable.manage_plava,
                R.drawable.teamwork_plava
        };

        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);

//        ViewPager viewPagerImages = rootView.findViewById(R.id.view_pager_imgs);
//        PagerAdapter pagerAdapter;
//        pagerAdapter = new ViewPagerAdapter(getActivity(), landing_pictures);
//        CirclePageIndicator mIndicator = rootView.findViewById(R.id.page_indicator);
//        viewPagerImages.setAdapter(pagerAdapter);
//        mIndicator.setViewPager(viewPagerImages);


//        if(Objects.equals(getIntent().getStringExtra("SIGNUP"), "signUp")){
//        }

        return rootView;
    }

}
