package com.example.codeacademyapp.main.group;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.GroupNameAdapter;
import com.example.codeacademyapp.main.GroupChatViewModel;
import com.example.codeacademyapp.main.group.add_tasks.AddNewTaskFragment;
import com.example.codeacademyapp.main.group.new_tasks.NewTaskActivity;
import com.example.codeacademyapp.sign_in.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends BaseFragment {

    private Button new_task_btn;
    private RecyclerView recyclerView;
    private ArrayList<String> list_of_chat_groups;
    private GroupNameAdapter adapter;

    private GroupChatViewModel groupChatViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_group, container, false);
        setTitle(R.string.my_group);
        new_task_btn = rootView.findViewById(R.id.new_task_button);
        new_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newTaskActivity();
            }
        });

        groupChatViewModel = ViewModelProviders.of(this).get(GroupChatViewModel.class);

        initializingViews(rootView);
        retrieveAndDisplayGroups();

        FloatingActionButton addNewTaskButton = rootView.findViewById(R.id.add_new_task_floating_btn);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTaskFragment addNewTaskFragment = new AddNewTaskFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.main_container, addNewTaskFragment).commit();
            }
        });

        return rootView;


    }

    private void initializingViews(View view) {
        new_task_btn = view.findViewById(R.id.new_task_button);
        list_of_chat_groups = new ArrayList<>();
        adapter = new GroupNameAdapter(list_of_chat_groups, getFragmentManager());
        recyclerView = view.findViewById(R.id.list_chat_groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void retrieveAndDisplayGroups() {

        groupChatViewModel.getGroupNames().observe(this, new Observer<Iterator<DataSnapshot>>() {
            @Override
            public void onChanged(Iterator<DataSnapshot> dataSnapshotIterator) {
                final Set<String> set = new HashSet<>();

                while (dataSnapshotIterator.hasNext()) {

                    set.add(dataSnapshotIterator.next().getKey());
                }

                list_of_chat_groups.clear();
                list_of_chat_groups.addAll(set);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void newTaskActivity() {
        Intent intent = new Intent(getContext(), NewTaskActivity.class);
        startActivity(intent);
    }
}
