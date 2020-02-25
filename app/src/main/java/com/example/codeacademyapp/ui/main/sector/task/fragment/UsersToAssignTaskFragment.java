package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.AssignedUsers;
import com.example.codeacademyapp.data.model.ModelFirebase;
import com.example.codeacademyapp.ui.main.sector.task.fragment.listeners.UsersToAssignDialogListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersToAssignTaskFragment extends DialogFragment{

    private RecyclerView usersListRV;
    private DatabaseReference usersRef;
    private List<AssignedUsers> assignedUsers;
    private String selectedUserId;


    public UsersToAssignTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users_to_assign_task, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar_users_list_assignTo_fragment);
        toolbar.setTitle("Assign the task to..");
        if(getContext()!=null){
            toolbar.setTitleTextColor((ContextCompat.getColor(getContext(), R.color.colorPrimary)));
        }

        assignedUsers = new ArrayList<>();
//
//        final Button assignUsersBtn = rootView.findViewById(R.id.assign_users_btn);
//        assignUsersBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UsersToAssignDialogListener listener = (UsersToAssignDialogListener) getTargetFragment();
//                if (listener != null) {
//                    listener.passListOfUsersToAddNewTaskFragment(assignedUserId);
//                }
//                if(getDialog()!=null){
//                    getDialog().dismiss();}
//            }
//        });

        usersRef= FirebaseDatabase.getInstance().getReference().child("Users");
        usersListRV = rootView.findViewById(R.id.users_list_task_toAssign_RV);
        usersListRV.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ModelFirebase> options =
                new FirebaseRecyclerOptions.Builder<ModelFirebase>()
                        .setQuery(usersRef, ModelFirebase.class)
                        .build();

        FirebaseRecyclerAdapter<ModelFirebase, FindFriendsViewHolder> adapter = new FirebaseRecyclerAdapter<ModelFirebase,FindFriendsViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final FindFriendsViewHolder holder, final int position, @NonNull ModelFirebase model) {

                holder.userNAme.setText(model.Name);
                holder.userGroup.setText(model.Sector);

                Picasso.get().load(model.image)
                        .placeholder(R.drawable.profile_image)
                        .into(holder.profileImage);

//                holder.selectUserToAssignCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                            if(buttonView.isChecked()){
//
////                                assignedUserId = getRef(holder.getAdapterPosition()).getKey();
//
////                                AssignedUsers assignedUser = new AssignedUsers(assignedUserId);
////
////                                assignedUser.setUserId(assignedUserId);
////                                assignedUsers.add(assignedUser);
//
//                            }
//                        }
//                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedUserId = getRef(holder.getAdapterPosition()).getKey();

                        UsersToAssignDialogListener listener = (UsersToAssignDialogListener) getTargetFragment();
                        if (listener != null) {
                            listener.passListOfUsersToAddNewTaskFragment(selectedUserId);
                        }
                        if(getDialog()!=null){
                            getDialog().dismiss();}
                    }
                });

//                holder.selectUserToAssignRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if(buttonView.isChecked()){
//
//                           assignedUserId = getRef(holder.getAdapterPosition()).getKey();
//
//                            UsersToAssignDialogListener listener = (UsersToAssignDialogListener) getTargetFragment();
//                            if (listener != null) {
//                                listener.passListOfUsersToAddNewTaskFragment(assignedUserId);
//                            }
//                            if(getDialog()!=null){
//                                getDialog().dismiss();}
//
////                            AssignedUsers assignedUser = new AssignedUsers(assignedUserId);
////
////                            assignedUser.setUserId(assignedUserId);
////                            assignedUsers.add(assignedUser);
//
//                        }
//                    }
//                });
            }

            @NonNull
            @Override
            public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users_list_task_to_assign, parent, false);
                FindFriendsViewHolder viewHolder = new FindFriendsViewHolder(view);
                return viewHolder;
            }
    };
        usersListRV.setAdapter(adapter);
        adapter.startListening();
}
    public  static class FindFriendsViewHolder extends RecyclerView.ViewHolder{

        TextView userNAme, userGroup;
        CircleImageView profileImage;
        CheckBox selectUserToAssignCB;
        RadioButton selectUserToAssignRB;

        FindFriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            userNAme=itemView.findViewById(R.id.user_profile_name);
            userGroup=itemView.findViewById(R.id.user_group);
            profileImage=itemView.findViewById(R.id.users_profile_image);
//            selectUserToAssignCB=itemView.findViewById(R.id.select_to_assign_checkbox);
//            selectUserToAssignRB=itemView.findViewById(R.id.select_to_assign_radioBtn);

        }
    }
}
