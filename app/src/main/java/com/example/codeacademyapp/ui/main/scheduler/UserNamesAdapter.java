package com.example.codeacademyapp.ui.main.scheduler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.UserModelFirebase;
import com.example.codeacademyapp.databinding.ItemUserNamesBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserNamesAdapter extends RecyclerView.Adapter<UserNamesAdapter.UserNamesHorizontalViewHolder> {
    List<UserModelFirebase> assignedUsers;

    public UserNamesAdapter(List<UserModelFirebase> assignedUsersIds) {
        this.assignedUsers = assignedUsersIds;
    }

    @NonNull
    @Override
    public UserNamesAdapter.UserNamesHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserNamesBinding userNamesBinding = ItemUserNamesBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new UserNamesHorizontalViewHolder(userNamesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserNamesAdapter.UserNamesHorizontalViewHolder holder, int position) {
        UserModelFirebase user = assignedUsers.get(holder.getAdapterPosition());

        holder.itemUserNamesBinding.username.setText(user.Name);

        Picasso.get().load(user.image)
                .placeholder(R.drawable.astronaut)
                .into( holder.itemUserNamesBinding.usersProfileImage);

    }

    @Override
    public int getItemCount() {
        return assignedUsers.size();
    }

    public class UserNamesHorizontalViewHolder extends RecyclerView.ViewHolder {
        ItemUserNamesBinding itemUserNamesBinding;
        public UserNamesHorizontalViewHolder(@NonNull ItemUserNamesBinding itemView) {
            super(itemView.getRoot());
            this.itemUserNamesBinding = itemView;
        }
    }
}
