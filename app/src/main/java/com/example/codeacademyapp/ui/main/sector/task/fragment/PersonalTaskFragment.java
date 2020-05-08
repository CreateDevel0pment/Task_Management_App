package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.os.Bundle;
import android.util.Log;
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

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalTaskFragment extends BaseFragment {

    private List<TaskInformation> tasks;

    private TaskViewModel taskViewModel;
    private QuoteViewModel quoteViewModel;

    private RecyclerView rvPersonalTasks;
    private String userId;
    private String name, group, timeCreated, taskPriority, description, endDate;
    private View rootView;

    public PersonalTaskFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setTitle("Tasks");

        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_personal_task, container, false);

        rvPersonalTasks = rootView.findViewById(R.id.personal_task_list_RV);

        UserInformationViewModel userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        userId = userInformationViewModel.getUserId();

        quoteViewModel = ViewModelProviders.of(this).get(QuoteViewModel.class);
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        tasks = new ArrayList<>();


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Tasks");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot taskDataSnapshot : dataSnapshot.getChildren()) {

                    description = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getDescription();
                    group = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getSector();
                    name = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getName();
                    timeCreated = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getTimeCreated();
                    taskPriority = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getTaskPriority();
                    endDate = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getEndDate();
                    String taskRef = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getTaskRef();
                    String docName = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getDocName();
                    String docPath = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getDocPath();
                    String docType = Objects.requireNonNull(taskDataSnapshot.getValue(TaskInformation.class)).getDocType();

                    TaskInformation task =
                            new TaskInformation(name, description, group, timeCreated,
                                    taskPriority, endDate, taskRef, docType,
                                    docName, docPath);
                    tasks.add(task);
                }

                if (tasks.isEmpty()) {
                    quoteViewModel.randomQuote.observe(PersonalTaskFragment.this, new Observer<Quote>() {
                        @Override
                        public void onChanged(Quote quote) {
                            TextView randomQuoteTV = rootView.findViewById(R.id.random_quote_tv_personal);
                            TextView authorQuoteTV = rootView.findViewById(R.id.random_quote_author_tv_personal);
                            LinearLayout quoteLinearLayout = rootView.findViewById(R.id.quote_linear_layout_personal);
                            randomQuoteTV.setText(String.format("%s%s\"", '"', quote.getEn()));
                            authorQuoteTV.setText(quote.getAuthor());
                            quoteLinearLayout.setVisibility(View.VISIBLE);
                            rvPersonalTasks.setVisibility(View.GONE);
                        }
                    });
                    quoteViewModel.loadRandomQuote();

                } else {
                    String completedCheck = "";
                    TaskAdapter taskAdapter = new TaskAdapter(getContext(), tasks, userId, taskViewModel, completedCheck);
                    rvPersonalTasks.setVisibility(View.VISIBLE);
                    rvPersonalTasks.setAdapter(taskAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvPersonalTasks.setLayoutManager(layoutManager);
        return rootView;

    }
}
