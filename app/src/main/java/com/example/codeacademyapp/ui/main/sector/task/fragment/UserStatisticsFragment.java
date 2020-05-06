package com.example.codeacademyapp.ui.main.sector.task.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.UserModelFirebase;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class UserStatisticsFragment extends BaseFragment {

    private RecyclerView usersListRV;
    private DatabaseReference usersRef;
    private String selectedUserId;
    private int personalTasksCount, completedTasksCount;

    public UserStatisticsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_statistics, container, false);

        usersRef= FirebaseDatabase.getInstance().getReference().child("Users");
        usersListRV = view.findViewById(R.id.users_list_stats_rv);
        usersListRV.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<UserModelFirebase> options =
                new FirebaseRecyclerOptions.Builder<UserModelFirebase>()
                        .setQuery(usersRef, UserModelFirebase.class)
                        .build();

        FirebaseRecyclerAdapter<UserModelFirebase, UserStatisticsFragment.FindFriendsViewHolder> adapter = new FirebaseRecyclerAdapter<UserModelFirebase, UserStatisticsFragment.FindFriendsViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final UserStatisticsFragment.FindFriendsViewHolder holder,
                                            final int position, @NonNull final UserModelFirebase model) {

                holder.userNAme.setText(model.Name);

                Picasso.get().load(model.image)
                        .placeholder(R.drawable.astronaut)
                        .into(holder.profileImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedUserId = getRef(holder.getAdapterPosition()).getKey();

                        DatabaseReference personalTasksRef = FirebaseDatabase.getInstance().getReference().child("Users").child(selectedUserId);
                        personalTasksRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              personalTasksCount = (int) dataSnapshot.child("Tasks").getChildrenCount();
                              completedTasksCount = (int) dataSnapshot.child("CompletedTasks").getChildrenCount();


                                UserStatsDetailsFragment statsDetailsFragment = new UserStatsDetailsFragment(model.image, model.Name,
                                        completedTasksCount, personalTasksCount);

                                if (getFragmentManager()!=null)
                                {
                                    getFragmentManager().beginTransaction().replace(R.id.task_fragments_container, statsDetailsFragment)
                                            .commit();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.w(TAG, "Failed to read value.", databaseError.toException());
                            }
                        });
                    }
                });
            }

            @NonNull
            @Override
            public UserStatisticsFragment.FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users_list, parent, false);
                return new FindFriendsViewHolder(view);
            }
        };
        usersListRV.setAdapter(adapter);
        adapter.startListening();
    }
    public  static class FindFriendsViewHolder extends RecyclerView.ViewHolder{

        TextView userNAme;
        CircleImageView profileImage;

        FindFriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            userNAme=itemView.findViewById(R.id.user_profile_name);
            profileImage=itemView.findViewById(R.id.users_profile_image);
        }
    }
}
