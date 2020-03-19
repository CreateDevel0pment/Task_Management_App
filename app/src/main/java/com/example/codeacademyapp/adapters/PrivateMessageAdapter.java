package com.example.codeacademyapp.adapters;

import android.content.Intent;
import android.net.Uri;
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

public class PrivateMessageAdapter extends RecyclerView.Adapter<PrivateMessageAdapter.MyHolder> {

    private List<PrivateMessages> mList;
    private FirebaseAuth auth;
    private String receiverImage;
    private String fromMessageType;
    private String fromUserId;
    private String messageSenderId;
    private PrivateMessages messages;

    public PrivateMessageAdapter(List<PrivateMessages> mList) {
        this.mList = mList;
    }


    @NonNull
    @Override
    public PrivateMessageAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_layout,parent, false);

        auth=FirebaseAuth.getInstance();


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PrivateMessageAdapter.MyHolder holder, int position) {


        messageSenderId = auth.getCurrentUser().getUid();
        messages = mList.get(holder.getAdapterPosition());

        fromUserId = messages.getFrom();
        fromMessageType = messages.getType();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(fromUserId);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("image")){

                    receiverImage =dataSnapshot.child("image").getValue().toString();

                    Picasso.get().load(receiverImage)
                            .placeholder(R.drawable.astronaut)
                            .into(holder.receiverProfileImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.receiver_message.setVisibility(View.INVISIBLE);
        holder.receiverProfileImage.setVisibility(View.INVISIBLE);
        holder.sender_message.setVisibility(View.INVISIBLE);
        holder.cardView.setVisibility(View.INVISIBLE);
        holder.receiver_time.setVisibility(View.INVISIBLE);
        holder.sender_time.setVisibility(View.INVISIBLE);

        if(fromMessageType.equals("text")){

            if(fromUserId.equals(messageSenderId)){

                holder.sender_message.setVisibility(View.VISIBLE);
                holder.sender_message.setBackgroundResource(R.drawable.sender_message_layout);
                holder.sender_message.setText(messages.getMessage());

            } else {

                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.cardView.setVisibility(View.VISIBLE);
                holder.receiver_message.setVisibility(View.VISIBLE);
                holder.receiver_message.setBackgroundResource(R.drawable.reciever_message_layout);
                holder.receiver_message.setText(messages.getMessage());
                holder.receiver_time.setVisibility(View.GONE);

            }
        }else if(fromMessageType.equals(".jpg")){

            if(fromUserId.equals(messageSenderId)){

                holder.sender_image_card.setVisibility(View.VISIBLE);
                holder.sender_doc_image.setVisibility(View.VISIBLE);
                Picasso.get().load(messages.getMessage()).into(holder.sender_doc_image);

            } else {

                holder.receiver_image_card.setVisibility(View.VISIBLE);
                holder.receiver_doc_image.setVisibility(View.VISIBLE);
                Picasso.get().load(messages.getMessage()).into(holder.receiver_doc_image);
                holder.sender_image_card.setVisibility(View.GONE);
                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.cardView.setVisibility(View.VISIBLE);
                holder.receiver_time.setVisibility(View.GONE);
            }

        }else{

            if(fromUserId.equals(messageSenderId)){

                holder.sender_message.setVisibility(View.VISIBLE);
                holder.sender_message.setBackgroundResource(R.drawable.sender_message_layout);
                holder.sender_message.setText(messages.getName());
                holder.sender_time.setVisibility(View.VISIBLE);
                holder.sender_time.setText(messages.getTime());

            } else {

                holder.sender_message.setVisibility(View.GONE);
                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.cardView.setVisibility(View.VISIBLE);
                holder.receiver_message.setVisibility(View.VISIBLE);
                holder.receiver_message.setBackgroundResource(R.drawable.reciever_message_layout);
                holder.receiver_message.setText(messages.getName());
                holder.receiver_time.setVisibility(View.VISIBLE);
                holder.receiver_time.setText(messages.getTime());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(mList.get(holder.getAdapterPosition()).getMessage()));
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView sender_message, receiver_message,receiver_time,sender_time;
        ImageView receiverProfileImage,sender_doc_image,receiver_doc_image;
        CardView cardView,sender_image_card,receiver_image_card;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            sender_message=itemView.findViewById(R.id.sender_message_text);
            receiver_message=itemView.findViewById(R.id.reciever_message_text);
            receiverProfileImage =itemView.findViewById(R.id.message_profile_image);
            cardView=itemView.findViewById(R.id.message_profile_image_card);
            receiver_time=itemView.findViewById(R.id.reciever_time);
            sender_time=itemView.findViewById(R.id.sender_time);
            sender_doc_image =itemView.findViewById(R.id.sender_doc);
            sender_image_card = itemView.findViewById(R.id.sender_doc_card);
            receiver_doc_image = itemView.findViewById(R.id.reciever_doc);
            receiver_image_card = itemView.findViewById(R.id.receiver_doc_card);

        }
    }
}
