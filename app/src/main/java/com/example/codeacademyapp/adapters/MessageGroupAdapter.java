package com.example.codeacademyapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.MessageFromGroup;
import com.example.codeacademyapp.ui.main.wall.PrivateChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageGroupAdapter extends RecyclerView.Adapter<MessageGroupAdapter.MyHolder> {

    private List<MessageFromGroup> mList;
    private Context context;
    private String currentUser;

    public MessageGroupAdapter(List<MessageFromGroup> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }


    @NonNull
    @Override
    public MessageGroupAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageGroupAdapter.MyHolder holder, int position) {

        final MessageFromGroup messages = mList.get(holder.getAdapterPosition());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser().getUid();


        holder.receiver_message.setVisibility(View.INVISIBLE);
        holder.receiverProfileImage.setVisibility(View.INVISIBLE);
        holder.sender_message.setVisibility(View.INVISIBLE);
        holder.cardView.setVisibility(View.INVISIBLE);
        holder.receiver_time.setVisibility(View.INVISIBLE);
        holder.sender_time.setVisibility(View.INVISIBLE);

        if (messages.getId().equals(currentUser)) {

            holder.sender_message.setVisibility(View.VISIBLE);
            holder.sender_message.setBackgroundResource(R.drawable.sender_message_layout);
            holder.sender_message.setText(messages.getMessage());

            holder.sender_time.setVisibility(View.VISIBLE);
            holder.sender_time.setText(messages.getTime());

        } else {

            holder.receiver_name.setVisibility(View.VISIBLE);
            holder.receiver_name.setText(messages.getName());
            holder.receiverProfileImage.setVisibility(View.VISIBLE);
            holder.receiver_message.setVisibility(View.VISIBLE);
            holder.cardView.setVisibility(View.VISIBLE);

            holder.receiver_time.setVisibility(View.VISIBLE);
            holder.receiver_time.setText(messages.getTime());

            holder.receiver_message.setBackgroundResource(R.drawable.reciever_message_layout);
            holder.receiver_message.setText(messages.getMessage());
            Picasso.get().load(messages.getImage()).into(holder.receiverProfileImage);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!messages.getId().equals(currentUser)) {
                    Intent intent = new Intent(context, PrivateChatActivity.class);
                    intent.putExtra("visit_user_id", messages.getId());
                    intent.putExtra("visit_user_name", messages.getName());

                    intent.putExtra("visit_user_image", messages.getImage());
                    intent.putExtra("visit_user_sector", messages.getSector());
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView sender_message, receiver_message, receiver_name, receiver_time, sender_time;
        ImageView receiverProfileImage;
        CardView cardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            sender_message = itemView.findViewById(R.id.sender_message_text);
            receiver_message = itemView.findViewById(R.id.reciever_message_text);
            receiverProfileImage = itemView.findViewById(R.id.message_profile_image);
            cardView = itemView.findViewById(R.id.message_profile_imagee);
            receiver_name = itemView.findViewById(R.id.reciever_name);
            receiver_time = itemView.findViewById(R.id.reciever_time);
            sender_time = itemView.findViewById(R.id.sender_time);

        }
    }
}
