package com.example.codeacademyapp.ui.main.wall;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class RequestsFragment extends Fragment {

    private RecyclerView requestList;

    String currentUserId;
    DatabaseReference chat_request_ref, usersRef;
    FirebaseAuth auth;


    public RequestsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_requests, container, false);

        auth=FirebaseAuth.getInstance();
        currentUserId=auth.getCurrentUser().getUid();

        usersRef=FirebaseDatabase.getInstance().getReference().child("Users");
        chat_request_ref= FirebaseDatabase.getInstance().getReference().child("Chat Requests");

        requestList=view.findViewById(R.id.chat_requests_list);
        requestList.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ModelFirebase> options =
                new FirebaseRecyclerOptions.Builder<ModelFirebase>()
                .setQuery(chat_request_ref.child(currentUserId),ModelFirebase.class)
                .build();

        FirebaseRecyclerAdapter<ModelFirebase,RequestsContactsHolder> andapter =
                new FirebaseRecyclerAdapter<ModelFirebase, RequestsContactsHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RequestsContactsHolder holder, int position, @NonNull ModelFirebase model) {

                holder.itemView.findViewById(R.id.request_accept_btn).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.request_cancel_btn).setVisibility(View.VISIBLE);

                final String list_user_id = getRef(position).getKey();

                DatabaseReference getTypeRef= getRef(position).child("request_type").getRef();

                getTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            String type = dataSnapshot.getValue().toString();

                            if(type.equals("received")){

                                usersRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.hasChild("image")){


                                            String requestUserImage= dataSnapshot.child("image").getValue().toString();
                                            String requestUserName= dataSnapshot.child("Name").getValue().toString();
                                            String requestUserSector= dataSnapshot.child("Sector").getValue().toString();


                                            holder.userNAme.setText(requestUserName);
                                            holder.userSector.setText(requestUserSector);
                                            Picasso.get().load(requestUserImage).into(holder.image);
                                        }
                                        else {
                                            String requestUserName= dataSnapshot.child("Name").getValue().toString();
                                            String requestUserSector= dataSnapshot.child("Sector").getValue().toString();

                                            holder.userNAme.setText(requestUserName);
                                            holder.userSector.setText(requestUserSector);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public RequestsContactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.users_desplay_layout,parent,false);

                return new RequestsContactsHolder(view);
            }
        };

        requestList.setAdapter(andapter);
        andapter.startListening();
    }


    public static class RequestsContactsHolder extends RecyclerView.ViewHolder{

        TextView userNAme, userSector;
        CircleImageView image;
        Button accept, cancel;

        public RequestsContactsHolder(@NonNull View itemView) {
            super(itemView);

            userNAme=itemView.findViewById(R.id.user_profile_name);
            userSector=itemView.findViewById(R.id.user_group);
            image=itemView.findViewById(R.id.users_profile_image);
            accept=itemView.findViewById(R.id.request_accept_btn);
            cancel=itemView.findViewById(R.id.request_cancel_btn);

        }
    }
}
