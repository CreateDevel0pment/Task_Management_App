package com.example.codeacademyapp.ui.main.group.task.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.adapters.TaskAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAllTaskFragment extends Fragment {

    List<TaskInformation> tasks;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    FirebaseUser userFb;

    RecyclerView rvTasks;
//    FirebaseRecyclerAdapter<TaskInformation, TaskViewHolder> adapter;

    public ViewAllTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_all_task, container, false);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("Tasks");

        rvTasks = rootView.findViewById(R.id.task_list_RV);
        tasks = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot taskDataSnapshot : dataSnapshot.getChildren()){


                    String description  = taskDataSnapshot.getValue(TaskInformation.class).getDescription();
                    String group  = taskDataSnapshot.getValue(TaskInformation.class).getGroup();
                    String note  = taskDataSnapshot.getValue(TaskInformation.class).getNote();
                    String name  = taskDataSnapshot.getValue(TaskInformation.class).getName();
                    String timeCreated  = taskDataSnapshot.getValue(TaskInformation.class).getTimeCreated();

                    TaskInformation task = new TaskInformation(name, description, note,  group, timeCreated);

                    tasks.add(task);
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                rvTasks.setLayoutManager(layoutManager);
                TaskAdapter taskAdapter = new TaskAdapter(getContext(), tasks);
                rvTasks.setAdapter(taskAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
//        FirebaseRecyclerOptions<TaskInformation> options =
//                new FirebaseRecyclerOptions.Builder<TaskInformation>()
//                        .setQuery(myRef, TaskInformation.class)
//                        .build();
//
//        adapter = new FirebaseRecyclerAdapter<TaskInformation, TaskViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i, @NonNull TaskInformation taskInformation) {
//
//                taskViewHolder.description.setText(taskInformation.getDescription());
//                taskViewHolder.note.setText(taskInformation.getNote());
//                taskViewHolder.group.setText(taskInformation.getGroup());
//
////                        holder.userNAme.setText(model.Name);
////                holder.userGroup.setText(model.Group);
////                Picasso.get().load(model.image)
////                        .placeholder(R.drawable.profile_image)
////                        .into(holder.profileImage);
//            }
//
//            @NonNull
//            @Override
//            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_task, parent, false);
//                TaskViewHolder viewHolder = new TaskViewHolder(view);
//                return viewHolder;
//
//            }
//        };
//
//        rvTasks.setAdapter(adapter);
//        adapter.startListening();
//

        return rootView;
    }

//    public static class TaskViewHolder extends RecyclerView.ViewHolder {
//
//        TextView description, note, group;
////        CircleImageView profileImage;
//
//        public TaskViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            description = itemView.findViewById(R.id.task_desc_item);
//            note = itemView.findViewById(R.id.task_note_item    );
//            group = itemView.findViewById(R.id.task_group_item);
//
////            userNAme = itemView.findViewById(R.id.user_profile_name);
////            userGroup = itemView.findViewById(R.id.user_group);
////            profileImage = itemView.findViewById(R.id.users_profile_image);
//
//        }
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }


}


