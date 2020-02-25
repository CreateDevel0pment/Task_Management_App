package com.example.codeacademyapp.ui.main.sector.task.fragment;


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
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskTabsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    private FloatingActionButton addNewTaskFloatbtn;

    public TaskTabsFragment() {
    }

    private View rootView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView!=null){
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_task_tabs, container, false);


        addNewTaskFloatbtn = rootView.findViewById(R.id.add_new_task_floating_btn);

        addNewTaskFloatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTaskFragment addNewTaskFragment = new AddNewTaskFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.task_fragments_container, addNewTaskFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        tabLayout = rootView.findViewById(R.id.tab_layout_new_tasks);
        viewPager = rootView.findViewById(R.id.view_pager_new_tasks);
        NewTaskPagerAdapter adapter = new NewTaskPagerAdapter(getFragmentManager(),
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
