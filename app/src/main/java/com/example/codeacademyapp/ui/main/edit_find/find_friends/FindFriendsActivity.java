package com.example.codeacademyapp.ui.main.edit_find.find_friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.ModelFirebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendsActivity extends AppCompatActivity {

    RecyclerView find_friends_recycler_list;
    Toolbar toolbar;
    FrameLayout layout;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        usersRef= FirebaseDatabase.getInstance().getReference().child("Users");

        toolbar = findViewById(R.id.toolbar_find_friends);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Employ");
        layout=findViewById(R.id.user_byId_container);

        find_friends_recycler_list=findViewById(R.id.find_friends_recycler);
        find_friends_recycler_list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ModelFirebase> options =
                new FirebaseRecyclerOptions.Builder<ModelFirebase>()
                        .setQuery(usersRef, ModelFirebase.class)
                        .build();

        FirebaseRecyclerAdapter<ModelFirebase, FindFriendsViewHolder> adapter = new FirebaseRecyclerAdapter<ModelFirebase, FindFriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FindFriendsViewHolder holder, final int position, @NonNull ModelFirebase model) {

                holder.userNAme.setText(model.Name);
                holder.userGroup.setText(model.Sector);
                Picasso.get().load(model.image)
                        .placeholder(R.drawable.profile_image)
                        .into(holder.profileImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        layout.setVisibility(View.VISIBLE);

                        String visit_user_id = getRef(holder.getAdapterPosition()).getKey();

                        UserByIdFragment fragment=new UserByIdFragment();

                        Bundle bundle=new Bundle();
                        bundle.putString("user_by_id",visit_user_id);
                        fragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.user_byId_container, fragment)
                                .commit();

                    }
                });

            }

            @NonNull
            @Override
            public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_desplay_layout, parent, false);
                FindFriendsViewHolder viewHolder = new FindFriendsViewHolder(view);
                return viewHolder;

            }
        };

        find_friends_recycler_list.setAdapter(adapter);
        adapter.startListening();
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
