package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.TaskAdapter;
import com.example.codeacademyapp.data.model.AssignedUsers;
import com.example.codeacademyapp.data.model.CompletedBy;
import com.example.codeacademyapp.data.model.Quote;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.ui.main.sector.task.QuoteViewModel;
import com.example.codeacademyapp.ui.main.sector.task.TaskViewModel;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.example.codeacademyapp.ui.sign_in_up.fragments.UserInformationViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAllTaskFragment extends BaseFragment {

    private List<TaskInformation> tasks;
    private DatabaseReference myRef;
    private TaskAdapter taskAdapter;

    private TaskViewModel taskViewModel;
    private UserInformationViewModel userInformationViewModel;

    private RecyclerView rvTasks;
    private String userSector, userId;
    private View rootView;

    public ViewAllTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }

        final QuoteViewModel quoteViewModel = ViewModelProviders.of(this).get(QuoteViewModel.class);

        rootView = inflater.inflate(R.layout.fragment_view_all_task, container, false);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        rvTasks = rootView.findViewById(R.id.task_list_RV);
        tasks = new ArrayList<>();

        userInformationViewModel.getUserInformation().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userSector = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                }

                myRef = FirebaseDatabase.getInstance().getReference().child("Tasks").child("GroupTasks").child(userSector);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tasks.clear();

                        for (DataSnapshot taskDataSnapshot : dataSnapshot.getChildren()) {

                            String description = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getDescription();
                            String group = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getSector();
                            String name = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getName();
                            String timeCreated = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getTimeCreated();
                            String taskPriority = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getTaskPriority();
                            String endDate = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getEndDate();
                            String taskRef = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getTaskRef();

                            TaskInformation task = new TaskInformation(name, description,
                                    group, timeCreated, taskPriority, endDate, taskRef);
                            tasks.add(task);
                        }

                        if (tasks.isEmpty()) {
                            quoteViewModel.randomQuote.observe(ViewAllTaskFragment.this, new Observer<Quote>() {
                                @Override
                                public void onChanged(Quote quote) {
                                    TextView randomQuoteTV = rootView.findViewById(R.id.random_quote_tv_projects);
                                    TextView authorQuoteTV = rootView.findViewById(R.id.random_quote_author_tv_projects);
                                    LinearLayout quoteLinearLayout = rootView.findViewById(R.id.quote_linear_layout_projects);
                                    randomQuoteTV.setText(String.format("%s%s\"", '"', quote.getEn()));
                                    authorQuoteTV.setText(quote.getAuthor());
                                    quoteLinearLayout.setVisibility(View.VISIBLE);
                                    rvTasks.setVisibility(View.GONE);
                                }
                            });
                            quoteViewModel.loadRandomQuote();
                        } else {
                            userId = userInformationViewModel.getUserId();
                            String completedCheck = "completeGONE";
                            taskAdapter = new TaskAdapter(getContext(), tasks, userId, taskViewModel, completedCheck);
                            taskAdapter.notifyDataSetChanged();
                            rvTasks.setVisibility(View.VISIBLE);
                            rvTasks.setAdapter(taskAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvTasks.setLayoutManager(layoutManager);
        return rootView;
    }
}



