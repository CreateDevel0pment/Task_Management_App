package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.NewTaskPagerAdapter;
import com.example.codeacademyapp.data.model.Task;
import com.example.codeacademyapp.ui.main.MainActivity;
import com.example.codeacademyapp.ui.main.sector.task.TaskActivity;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.example.codeacademyapp.ui.sign_in_up.fragments.UserInformationViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskTabsFragment extends BaseFragment {

    private ViewPager viewPager;
    private String userPosition;


    private FloatingActionButton userStatsFloatingBtn;

    public TaskTabsFragment() {
    }

    private View rootView;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_task_tabs, container, false);

        UserInformationViewModel userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        String userID = userInformationViewModel.getUserId();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    userPosition = Objects.requireNonNull(dataSnapshot.child("Position").getValue()).toString();

                    if (userPosition.equals("Manager")) {

                        userStatsFloatingBtn = rootView.findViewById(R.id.user_stats_floating_btn);
                        userStatsFloatingBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), TaskActivity.class);
                                intent.putExtra("openStatsFrag", "openStatsFrag");
                                startActivity(intent);

                            }
                        });
                        userStatsFloatingBtn.show();
                    }
                }
            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout_new_tasks);
        viewPager = rootView.findViewById(R.id.view_pager_new_tasks);
        assert getFragmentManager() != null;
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
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        return rootView;
    }

}
