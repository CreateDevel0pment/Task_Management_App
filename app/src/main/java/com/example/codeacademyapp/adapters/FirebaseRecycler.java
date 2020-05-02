package com.example.codeacademyapp.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.UserModelFirebase;
import com.example.codeacademyapp.ui.main.edit_find.find_friends.UserByIdFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.codeacademyapp.utils.Constants.USER_BY_ID;

public class FirebaseRecycler extends
        com.firebase.ui.database.FirebaseRecyclerAdapter<UserModelFirebase,
                FirebaseRecycler.FindFriendsViewHolder> {

    private FragmentManager fragmentManager;


    public FirebaseRecycler(@NonNull FirebaseRecyclerOptions<UserModelFirebase> options, FragmentManager fragmentManager) {
        super(options);
        this.fragmentManager=fragmentManager;
    }

    @Override
    protected void onBindViewHolder(@NonNull final FindFriendsViewHolder holder, int position, @NonNull UserModelFirebase model) {

        holder.userNAme.setText(model.Name);
        holder.userGroup.setText(model.Sector);
        Picasso.get().load(model.image)
                .placeholder(R.drawable.astronaut)
                .into(holder.profileImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String visit_user_id = getRef(holder.getAdapterPosition()).getKey();

                UserByIdFragment fragment=new UserByIdFragment();

                Bundle bundle=new Bundle();
                bundle.putString(USER_BY_ID,visit_user_id);
                fragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.user_byId_container, fragment)
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }

    @NonNull
    @Override
    public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_request_layout, parent, false);
        return new FindFriendsViewHolder(view);
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
