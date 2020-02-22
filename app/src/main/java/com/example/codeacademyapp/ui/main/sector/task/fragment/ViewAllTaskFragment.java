package com.example.codeacademyapp.ui.main.sector.task.fragment;


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
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.example.codeacademyapp.ui.main.sector.task.QuoteViewModel;
import com.example.codeacademyapp.ui.main.sector.task.TaskViewModel;
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
public class ViewAllTaskFragment extends Fragment {

    private List<TaskInformation> tasks;
    private DatabaseReference myRef;
    private List<AssignedUsers> assignedUsersList;
    private List<CompletedBy> completedByList;
    private TaskAdapter taskAdapter;
    private TaskViewModel taskViewModel;

    private RecyclerView rvTasks;
    private String userSector, userId, id;
    private View rootView;

    public ViewAllTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final QuoteViewModel quoteViewModel = ViewModelProviders.of(this).get(QuoteViewModel.class);

        rootView = inflater.inflate(R.layout.fragment_view_all_task, container, false);
        ChatViewModel groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        groupChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userSector = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                }
                userId = dataSnapshot.getKey();
                assignedUsersList = new ArrayList<>();
                completedByList = new ArrayList<>();
                rvTasks = rootView.findViewById(R.id.task_list_RV);
                tasks = new ArrayList<>();
                myRef = FirebaseDatabase.getInstance().getReference().child("Tasks").child("GroupTasks").child(userSector);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tasks.clear();

                        for (DataSnapshot taskDataSnapshot : dataSnapshot.getChildren()) {

                            assignedUsersList = taskDataSnapshot.getValue(TaskInformation.class).getAssignedUsers();
                            completedByList = taskDataSnapshot.getValue(TaskInformation.class).getCompletedBy();
                            String description = taskDataSnapshot.getValue(TaskInformation.class).getDescription();
                            String group = taskDataSnapshot.getValue(TaskInformation.class).getSector();
                            String name = taskDataSnapshot.getValue(TaskInformation.class).getName();
                            String timeCreated = taskDataSnapshot.getValue(TaskInformation.class).getTimeCreated();
                            String taskPriority = taskDataSnapshot.getValue(TaskInformation.class).getTaskPriority();
                            String endDate = taskDataSnapshot.getValue(TaskInformation.class).getEndDate();
                            String taskRef = taskDataSnapshot.getValue(TaskInformation.class).getTaskRef();


                            TaskInformation task = new TaskInformation(name, description,
                                    group, timeCreated, taskPriority, endDate, taskRef);
                            tasks.add(task);

//                    if (completedByList != null) {
//                        for (int i = 0; i < completedByList.size(); i++) {
//                            CompletedBy completedBy;
//                            completedBy = completedByList.get(i);
//                            id = completedBy.getUserId();
//                        }
//                        if (!id.equals(userId)) {
//                            if (assignedUsersList == null) {
//                                if (group != null) {
//                                    if (group.equals(userSector)) {
//                                        TaskInformation task = new TaskInformation(name, description,
//                                                note, group, timeCreated, taskPriority, endDate);
//                                        tasks.add(task);
//                                    }
//                                }
//                            }
//                        }
//
//                    } else {
//                        if (assignedUsersList == null) {
//                            if (group != null) {
//                                if (group.equals(userSector)) {
//
//                                }
//                            }
//                        }
//                    }
                        }

                        if (tasks.isEmpty()) {
                            quoteViewModel.randomQuote.observe(ViewAllTaskFragment.this, new Observer<Quote>() {
                                @Override
                                public void onChanged(Quote quote) {
                                    TextView randomQuoteTV = rootView.findViewById(R.id.random_quote_tv);
                                    TextView authorQuoteTV = rootView.findViewById(R.id.random_quote_author_tv);
                                    LinearLayout quoteLinearLayout = rootView.findViewById(R.id.quote_linear_layout);
                                    randomQuoteTV.setText(String.format("%s%s\"", '"', quote.getEn()));
                                    authorQuoteTV.setText(quote.getAuthor());
                                    quoteLinearLayout.setVisibility(View.VISIBLE);
                                    rvTasks.setVisibility(View.GONE);
                                }
                            });
                            quoteViewModel.loadRandomQuote();

                        } else {
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rvTasks.setLayoutManager(layoutManager);
                            String completedCheck = "";
                            taskAdapter = new TaskAdapter(getContext(), tasks, getFragmentManager(), userId, taskViewModel, completedCheck);
                            taskAdapter.notifyDataSetChanged();
                            rvTasks.setAdapter(taskAdapter);
                        }
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



