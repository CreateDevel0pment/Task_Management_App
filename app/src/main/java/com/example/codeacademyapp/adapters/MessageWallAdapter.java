package com.example.codeacademyapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.MessagesFromWall;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageWallAdapter extends RecyclerView.Adapter<MessageWallAdapter.MyHolder> {

    private List<MessagesFromWall> mList;

    public MessageWallAdapter(List<MessagesFromWall> mList) {
        this.mList = mList;
    }


    @NonNull
    @Override
    public MessageWallAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_wall, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageWallAdapter.MyHolder holder, int position) {

        MessagesFromWall messages = mList.get(holder.getAdapterPosition());

        holder.messageContent.setText(messages.getMessage());
        holder.userName.setText(messages.getName());
        holder.userSector.setText(messages.getGroup());
        holder.messageContent.setText(messages.getMessage());
        if (messages.getImage() != null) {

            Picasso.get().load(messages.getImage())
                    .into(holder.profileImage);
        } else {

            Picasso.get().load(R.drawable.profile_image)
                    .into(holder.profileImage);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView userName, messageContent, userSector;
        ImageView profileImage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.wall_user_name);
            messageContent = itemView.findViewById(R.id.wall_message_text);
            userSector = itemView.findViewById(R.id.wall_sector);
            userSector.setVisibility(View.VISIBLE);
            profileImage = itemView.findViewById(R.id.wall_profile_image);

        }
    }
}
