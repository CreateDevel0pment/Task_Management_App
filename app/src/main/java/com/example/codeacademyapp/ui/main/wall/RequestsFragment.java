package com.example.codeacademyapp.ui.main.wall;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.ModelFirebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    private RecyclerView requestList;

    private String currentUserId;
    private String list_user_id;
    private DatabaseReference chat_request_ref,
            usersRef,
            contactRef;
    private FirebaseAuth auth;

    private TextView no_text;
    private CircleImageView avatar_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_requests, container, false);

        no_text=view.findViewById(R.id.request_text);
        avatar_image=view.findViewById(R.id.request_circular_image);

        auth = FirebaseAuth.getInstance();
        currentUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        chat_request_ref = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        contactRef = FirebaseDatabase.getInstance().getReference().child("Contacts");

        requestList = view.findViewById(R.id.chat_requests_list);
        requestList.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final FirebaseRecyclerOptions<ModelFirebase> options =
                new FirebaseRecyclerOptions.Builder<ModelFirebase>()
                        .setQuery(chat_request_ref.child(currentUserId), ModelFirebase.class)
                        .build();

            final FirebaseRecyclerAdapter<ModelFirebase, RequestsContactsHolder> adapter =
                    new FirebaseRecyclerAdapter<ModelFirebase, RequestsContactsHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final RequestsContactsHolder holder, int position, @NonNull final ModelFirebase model) {



                            holder.itemView.findViewById(R.id.request_accept_btn).setVisibility(View.VISIBLE);
                            holder.itemView.findViewById(R.id.request_accept_btn).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    acceptTheRequest();

                                }
                            });

                            holder.itemView.findViewById(R.id.request_cancel_btn).setVisibility(View.VISIBLE);
                            holder.itemView.findViewById(R.id.request_cancel_btn).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    cancelRequest();

                                }
                            });

                            list_user_id = getRef(position).getKey();

                            DatabaseReference getTypeRef = getRef(position).child("request_type").getRef();

                            getTypeRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                            if (list_user_id != null) {
                                                usersRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                        if (dataSnapshot.exists()) {

                                                            no_text.setVisibility(View.GONE);
                                                            avatar_image.setVisibility(View.GONE);


                                                            if (dataSnapshot.hasChild("image")) {

                                                                String requestUserImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                                                                String requestUserName = Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString();
                                                                String requestUserSector = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();

                                                                holder.userNAme.setText(requestUserName);
                                                                holder.userSector.setText(requestUserSector);
                                                                Picasso.get().load(requestUserImage).into(holder.image);
                                                            }

                                                            String requestUserName = Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString();
                                                            String requestUserSector = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();

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

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @NonNull
                        @Override
                        public RequestsContactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_request_layout, parent, false);

                            return new RequestsContactsHolder(view);
                        }
                    };

            requestList.setAdapter(adapter);
            adapter.startListening();

    }

    private void acceptTheRequest() {

        contactRef.child(currentUserId).child(list_user_id).child("Contacts")
                .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {


                    contactRef.child(list_user_id).child(currentUserId).child("Contacts")
                            .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                chat_request_ref.child(currentUserId).child(list_user_id)
                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            chat_request_ref.child(list_user_id).child(currentUserId)
                                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {

                                                        Toast.makeText(getContext(), "Contact saved", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                        }

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }

    private void cancelRequest() {

        chat_request_ref.child(currentUserId).child(list_user_id)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    chat_request_ref.child(list_user_id).child(currentUserId)
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(getContext(), "Contact Deleted", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }


    public static class RequestsContactsHolder extends RecyclerView.ViewHolder {

        TextView userNAme, userSector;
        CircleImageView image;
        Button accept, cancel;

        public RequestsContactsHolder(@NonNull View itemView) {
            super(itemView);

            userNAme = itemView.findViewById(R.id.user_profile_name);
            userSector = itemView.findViewById(R.id.user_group);
            image = itemView.findViewById(R.id.users_profile_image);
            accept = itemView.findViewById(R.id.request_accept_btn);
            cancel = itemView.findViewById(R.id.request_cancel_btn);

        }
    }
}
