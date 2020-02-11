package com.example.codeacademyapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.Messages;
import com.example.codeacademyapp.data.model.MessagesFromWall;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageWallAdapter extends RecyclerView.Adapter<MessageWallAdapter.MyHolder> {

    private List<MessagesFromWall> mList;
    FirebaseAuth auth;
    DatabaseReference userrsRef;

    public MessageWallAdapter(List<MessagesFromWall> mList) {
        this.mList = mList;
    }


    @NonNull
    @Override
    public MessageWallAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_layout,parent, false);

        auth=FirebaseAuth.getInstance();


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageWallAdapter.MyHolder holder, int position) {

        String messageSenderId = auth.getCurrentUser().getUid();

        MessagesFromWall messages=mList.get(holder.getAdapterPosition());

        holder.sender_message.setText(messages.getMessage());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView sender_message, receiver_message;
        CircleImageView reciverProfileImage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            sender_message=itemView.findViewById(R.id.sender_message_text);
            receiver_message=itemView.findViewById(R.id.reciever_message_text);
            reciverProfileImage=itemView.findViewById(R.id.message_profile_image);
        }
    }
}
