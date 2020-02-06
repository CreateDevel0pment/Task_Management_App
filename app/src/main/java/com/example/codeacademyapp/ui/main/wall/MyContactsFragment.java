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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyContactsFragment extends Fragment {

    private DatabaseReference contactsRef,usersRef;
    private FirebaseAuth auth;
    private String currentUserId;


    public MyContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_my_contacts, container, false);


        RecyclerView my_contact_list = view.findViewById(R.id.my_contact_list);
        my_contact_list.setLayoutManager(new LinearLayoutManager(getContext()));

        auth=FirebaseAuth.getInstance();
        currentUserId=auth.getCurrentUser().getUid();
        contactsRef= FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserId);
        usersRef=FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<ModelFirebase>()
                        .setQuery(contactsRef,ModelFirebase.class)
                        .build();

        FirebaseRecyclerAdapter<ModelFirebase,MyContactsHolder> adapter=
                new FirebaseRecyclerAdapter<ModelFirebase, MyContactsHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final MyContactsHolder holder, int position, @NonNull ModelFirebase model) {

                        final String usersIds=getRef(position).getKey();

                        usersRef.child(usersIds).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.hasChild("image")){

                                    String profileImage= dataSnapshot.child("image").getValue().toString();
                                    String userName= dataSnapshot.child("Name").getValue().toString();
                                    String userSector= dataSnapshot.child("Sector").getValue().toString();

                                    holder.userNAme.setText(userName);
                                    holder.userSector.setText(userSector);
                                    Picasso.get().load(profileImage).into(holder.image);
                                }else {
                                    String userName= dataSnapshot.child("Name").getValue().toString();
                                    String userSector= dataSnapshot.child("Sector").getValue().toString();

                                    holder.userNAme.setText(userName);
                                    holder.userSector.setText(userSector);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    @NonNull
                    @Override
                    public MyContactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_display_layout,parent,false);

                        return new MyContactsHolder(view);
                    }
                };

        my_contact_list.setAdapter(adapter);
        adapter.startListening();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    public static class MyContactsHolder extends RecyclerView.ViewHolder{

        TextView userNAme, userSector;
        CircleImageView image;

        public MyContactsHolder(@NonNull View itemView) {
            super(itemView);

            userNAme=itemView.findViewById(R.id.user_profile_name);
            userSector=itemView.findViewById(R.id.user_group);
            image=itemView.findViewById(R.id.users_profile_image);
        }
    }
}
