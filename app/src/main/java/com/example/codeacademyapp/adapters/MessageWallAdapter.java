package com.example.codeacademyapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.MessagesFromWall;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageWallAdapter extends RecyclerView.Adapter<MessageWallAdapter.MyHolder> {

    private List<MessagesFromWall> mList;

    public MessageWallAdapter(List<MessagesFromWall> mList) {
        this.mList = mList;
    }


    @NonNull
    @Override
    public MessageWallAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_layout, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageWallAdapter.MyHolder holder, int position) {

        MessagesFromWall messages = mList.get(holder.getAdapterPosition());
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String currenUser=auth.getCurrentUser().getUid();

        holder.receiver_message.setVisibility(View.INVISIBLE);
        holder.reciverProfileImage.setVisibility(View.INVISIBLE);
        holder.sender_message.setVisibility(View.INVISIBLE);
        holder.cardView.setVisibility(View.INVISIBLE);
        holder.receiver_time.setVisibility(View.INVISIBLE);
        holder.sender_time.setVisibility(View.INVISIBLE);

        if(messages.getId().equals(currenUser)){


            holder.sender_message.setVisibility(View.VISIBLE);
            holder.sender_message.setBackgroundResource(R.drawable.sender_message_layout);
            holder.sender_message.setText(messages.getMessage());

            holder.sender_time.setVisibility(View.VISIBLE);
            holder.sender_time.setText(messages.getTime());

        }else {

            holder.receiver_name.setVisibility(View.VISIBLE);
            holder.receiver_name.setText(messages.getName());

            holder.receiver_sector.setVisibility(View.VISIBLE);
            holder.receiver_sector.setText(messages.getSector());

            holder.reciverProfileImage.setVisibility(View.VISIBLE);
            holder.receiver_message.setVisibility(View.VISIBLE);
            holder.cardView.setVisibility(View.VISIBLE);

            holder.receiver_time.setVisibility(View.VISIBLE);
            holder.receiver_time.setText(messages.getTime());

            holder.receiver_message.setBackgroundResource(R.drawable.reciever_message_layout);
            holder.receiver_message.setText(messages.getMessage());
            Picasso.get().load(messages.getImage()).into(holder.reciverProfileImage);

        }

//        holder.messageContent.setText(messages.getMessage());
//        holder.userName.setText(messages.getName());
//        holder.userSector.setText(messages.getSector());
//        holder.messageContent.setText(messages.getMessage());
//        if (messages.getImage() != null) {
//
//            Picasso.get().load(messages.getImage())
//                    .into(holder.profileImage);
//        } else {
//
//            Picasso.get().load(R.drawable.profile_image)
//                    .into(holder.profileImage);
//        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView sender_message, receiver_message,receiver_name,receiver_sector,receiver_time,sender_time;
        ImageView reciverProfileImage;
        CardView cardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            sender_message=itemView.findViewById(R.id.sender_message_text);
            receiver_message=itemView.findViewById(R.id.reciever_message_text);
            reciverProfileImage=itemView.findViewById(R.id.message_profile_image);
            cardView=itemView.findViewById(R.id.message_profile_imagee);
            receiver_name=itemView.findViewById(R.id.reciever_name);
            receiver_sector=itemView.findViewById(R.id.reciever_sector);
            receiver_time=itemView.findViewById(R.id.reciever_time);
            sender_time=itemView.findViewById(R.id.sender_time);

        }
    }
}
