package com.example.codeacademyapp.ui.main.group.task.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.NewTaskPagerAdapter;
import com.example.codeacademyapp.ui.main.MainActivity;
import com.example.codeacademyapp.ui.main.group.task.TaskActivity;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskTabsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentManager fragmentManager;

    public TaskTabsFragment(FragmentManager supportFragmentManager) {
        this.fragmentManager = supportFragmentManager;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_task_tabs, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar_tasks_tab_fragment);
        toolbar.setTitle("Back to group chat");
        toolbar.setTitleTextColor((ContextCompat.getColor(getContext(), R.color.white)));

        toolbar.setNavigationIcon(R.drawable.ic_back_button_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.putExtra("inflateGroupChat", "inflateGroupChat");
//                startActivity(intent);

                getActivity().onBackPressed();
            }
        });

        tabLayout = rootView.findViewById(R.id.tab_layout_new_tasks);
        viewPager = rootView.findViewById(R.id.view_pager_new_tasks);
        NewTaskPagerAdapter adapter = new NewTaskPagerAdapter(fragmentManager,
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

        return rootView;



    }

}
