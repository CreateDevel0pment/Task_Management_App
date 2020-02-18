package com.example.codeacademyapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.MessageFromGroup;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageGroupAdapter extends RecyclerView.Adapter<MessageGroupAdapter.MyHolder> {

    private List<MessageFromGroup> mList;

    public MessageGroupAdapter(List<MessageFromGroup> mList) {
        this.mList = mList;
    }


    @NonNull
    @Override
    public MessageGroupAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_wall, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageGroupAdapter.MyHolder holder, int position) {

        final MessageFromGroup messages = mList.get(holder.getAdapterPosition());

        holder.userName.setText(messages.getName());
        holder.messageContent.setText(messages.getMessage());
        Picasso.get().load(messages.getImage())
                .placeholder(R.drawable.profile_image)
                .into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView userName, messageContent, userSector;
        CircleImageView profileImage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.wall_user_name);
            messageContent = itemView.findViewById(R.id.wall_message_text);
            userSector = itemView.findViewById(R.id.wall_sector);
            profileImage = itemView.findViewById(R.id.wall_profile_image);

        }
    }
}
