package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
public class CompletedTasksFragment extends BaseFragment {


    private List<TaskInformation> tasks;
    private DatabaseReference myRef;
    private TaskAdapter taskAdapter;
    private QuoteViewModel quoteViewModel;
    private TaskViewModel taskViewModel;
    private ProgressBar progressBar;

    private RecyclerView rvTasks;
    private String userId;

    private View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView!=null) {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_completed_tasks, container, false);

        quoteViewModel = ViewModelProviders.of(this).get(QuoteViewModel.class);
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        progressBar = rootView.findViewById(R.id.task_completed_progress_bar);


        UserInformationViewModel userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        userInformationViewModel.getUserInformation().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                userId = dataSnapshot.getKey();
                myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("CompletedTasks");
                rvTasks = rootView.findViewById(R.id.tasks_completed_list_RV);
                tasks = new ArrayList<>();

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
                            quoteViewModel.randomQuote.observe(CompletedTasksFragment.this, new Observer<Quote>() {
                                @Override
                                public void onChanged(Quote quote) {
                                    TextView randomQuoteTV = rootView.findViewById(R.id.random_quote_tv_completed);
                                    TextView authorQuoteTV = rootView.findViewById(R.id.random_quote_author_tv_completed);
                                    LinearLayout quoteLinearLayout = rootView.findViewById(R.id.quote_linear_layout_completed);
                                    randomQuoteTV.setText(String.format("%s%s\"", '"', quote.getEn()));
                                    authorQuoteTV.setText(quote.getAuthor());
                                    progressBar.setVisibility(View.GONE);
                                    quoteLinearLayout.setVisibility(View.VISIBLE);
                                }
                            });
                            quoteViewModel.loadRandomQuote();
                        }
                        else
                            {RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rvTasks.setLayoutManager(layoutManager);
                            String completedCheck = "completeGONE";
                            taskAdapter = new TaskAdapter(getContext(), tasks, userId, taskViewModel, completedCheck);
                                progressBar.setVisibility(View.GONE);
                                rvTasks.setVisibility(View.VISIBLE);
                                rvTasks.setAdapter(taskAdapter);}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        return rootView;
    }

}
