package com.example.codeacademyapp.ui.sign_in_up;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.TabPagerAdapter;
import com.example.codeacademyapp.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    int[] landing_pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
        landing_pictures = new int[]{
                R.drawable.comunicate_plava,
                R.drawable.manage_plava,
                R.drawable.teamwork_plava
        };
        ViewPager viewPagerImages = findViewById(R.id.view_pager_imgs);
        PagerAdapter pagerAdapter;
        pagerAdapter = new ViewPagerAdapter(this, landing_pictures);
        CirclePageIndicator mIndicator = findViewById(R.id.page_indicator);
        viewPagerImages.setAdapter(pagerAdapter);
        mIndicator.setViewPager(viewPagerImages);

        if(Objects.equals(getIntent().getStringExtra("SIGNUP"), "signUp")){
            viewPager.setCurrentItem(1);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}