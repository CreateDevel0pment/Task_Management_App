package com.example.codeacademyapp.group;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.GroupNameAdapter;
import com.example.codeacademyapp.add_tasks.AddNewTaskFragment;
import com.example.codeacademyapp.new_tasks.NewTaskActivity;
import com.example.codeacademyapp.sign_in.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private DatabaseReference group_ref;

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

        group_ref = FirebaseDatabase.getInstance().getReference().child("Groups");

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
        adapter=new GroupNameAdapter(list_of_chat_groups, getFragmentManager());
        recyclerView = view.findViewById(R.id.list_chat_groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void retrieveAndDisplayGroups() {

        group_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while ((iterator.hasNext())) {

                    set.add(iterator.next().getKey());
                }
                list_of_chat_groups.clear();
                list_of_chat_groups.addAll(set);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void newTaskActivity() {
        Intent intent = new Intent(getContext(), NewTaskActivity.class);
        startActivity(intent);
    }
}
