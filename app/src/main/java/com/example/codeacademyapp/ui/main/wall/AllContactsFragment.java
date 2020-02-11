package com.example.codeacademyapp.ui.main.wall;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.ModelFirebase;
import com.example.codeacademyapp.ui.main.edit_find.find_friends.UserByIdFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllContactsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_all_contacts, container, false);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        RecyclerView find_friends_recycler_list = view.findViewById(R.id.find_friends_recycler);
        find_friends_recycler_list.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<ModelFirebase> options =
                new FirebaseRecyclerOptions.Builder<ModelFirebase>()
                        .setQuery(usersRef, ModelFirebase.class)
                        .build();

        FirebaseRecyclerAdapter<ModelFirebase, AllContactsFragment.FindFriendsViewHolder> adapter = new FirebaseRecyclerAdapter<ModelFirebase, AllContactsFragment.FindFriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final AllContactsFragment.FindFriendsViewHolder holder, final int position, @NonNull ModelFirebase model) {

                holder.userNAme.setText(model.Name);
                holder.userGroup.setText(model.Sector);
                Picasso.get().load(model.image)
                        .placeholder(R.drawable.profile_image)
                        .into(holder.profileImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String visit_user_id = getRef(holder.getAdapterPosition()).getKey();

                        UserByIdFragment fragment=new UserByIdFragment();

                        Bundle bundle=new Bundle();
                        bundle.putString("user_by_id",visit_user_id);
                        fragment.setArguments(bundle);

                        if (getActivity() != null) {
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.wall_container, fragment)
                                    .commit();
                        }

                    }
                });

            }

            @NonNull
            @Override
            public AllContactsFragment.FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_display_layout, parent, false);
                AllContactsFragment.FindFriendsViewHolder viewHolder = new AllContactsFragment.FindFriendsViewHolder(view);
                return viewHolder;

            }

            @Override
            public void onError(@NonNull DatabaseError error) {
                super.onError(error);
            }
        };

        find_friends_recycler_list.setAdapter(adapter);
        adapter.startListening();


        return view;
    }

    public  static class FindFriendsViewHolder extends RecyclerView.ViewHolder{

        TextView userNAme, userGroup;
        CircleImageView profileImage;

        public FindFriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            userNAme=itemView.findViewById(R.id.user_profile_name);
            userGroup=itemView.findViewById(R.id.user_group);
            profileImage=itemView.findViewById(R.id.users_profile_image);

        }
    }

}
