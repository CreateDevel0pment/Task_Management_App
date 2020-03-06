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
import com.example.codeacademyapp.data.model.PrivateMessages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder> {

    private List<PrivateMessages> mList;
    private FirebaseAuth auth;

    public MessageAdapter(List<PrivateMessages> mList) {
        this.mList = mList;
    }


    @NonNull
    @Override
    public MessageAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_layout,parent, false);

        auth=FirebaseAuth.getInstance();


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.MyHolder holder, int position) {

        String messageSenderId = auth.getCurrentUser().getUid();

        PrivateMessages messages=mList.get(holder.getAdapterPosition());

        String fromUserId=messages.getFrom();
        String fromMessageType=messages.getType();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(fromUserId);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("image")){

                    String recieverImage=dataSnapshot.child("image").getValue().toString();

                    Picasso.get().load(recieverImage)
                            .placeholder(R.drawable.profile_image)
                            .into(holder.reciverProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(fromMessageType.equals("text")){

            holder.receiver_message.setVisibility(View.INVISIBLE);
            holder.reciverProfileImage.setVisibility(View.INVISIBLE);
            holder.sender_message.setVisibility(View.INVISIBLE);
            holder.cardView.setVisibility(View.INVISIBLE);
            holder.receiver_time.setVisibility(View.INVISIBLE);
            holder.sender_time.setVisibility(View.INVISIBLE);

            if(fromUserId.equals(messageSenderId)){

                holder.sender_message.setVisibility(View.VISIBLE);
                holder.sender_message.setBackgroundResource(R.drawable.sender_message_layout);
                holder.sender_message.setText(messages.getMessage());


            }else {

                holder.reciverProfileImage.setVisibility(View.VISIBLE);
                holder.receiver_message.setVisibility(View.VISIBLE);
                holder.cardView.setVisibility(View.VISIBLE);
                holder.receiver_message.setBackgroundResource(R.drawable.reciever_message_layout);
                holder.receiver_message.setText(messages.getMessage());

            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView sender_message, receiver_message,receiver_time,sender_time;
        ImageView reciverProfileImage;
        CardView cardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            sender_message=itemView.findViewById(R.id.sender_message_text);
            receiver_message=itemView.findViewById(R.id.reciever_message_text);
            reciverProfileImage=itemView.findViewById(R.id.message_profile_image);
            cardView=itemView.findViewById(R.id.message_profile_imagee);
            receiver_time=itemView.findViewById(R.id.reciever_time);
            sender_time=itemView.findViewById(R.id.sender_time);
        }
    }
}
