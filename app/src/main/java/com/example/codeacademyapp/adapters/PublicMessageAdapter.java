package com.example.codeacademyapp.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.PublicMessage;
import com.example.codeacademyapp.ui.main.wall.PrivateChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class PublicMessageAdapter extends RecyclerView.Adapter<PublicMessageAdapter.MyHolder> {

    private List<PublicMessage> mList;
    private String checkIfContinuedMessages, prevMessage;

    private FragmentManager fragmentManager;

    public PublicMessageAdapter(List<PublicMessage> mList, FragmentManager fragmentManager) {
        this.mList = mList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public PublicMessageAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_layout, parent, false);

        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final PublicMessageAdapter.MyHolder holder, final int position) {

        final PublicMessage messages = mList.get(holder.getAdapterPosition());
        prevMessage = mList.get(holder.getAdapterPosition()).getId();
        if (position > 0) {
            checkIfContinuedMessages = mList.get(holder.getAdapterPosition() - 1).getId();
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String currenUser = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        holder.receiver_message.setVisibility(View.GONE);
        holder.receiverProfileImage.setVisibility(View.GONE);
        holder.sender_message.setVisibility(View.GONE);
        holder.cardView.setVisibility(View.GONE);
        holder.sender_time.setVisibility(View.GONE);
        holder.receiver_name.setVisibility(View.GONE);
        holder.receiver_sector.setVisibility(View.GONE);


        if (messages.getId().equals(currenUser)) {

            if (messages.getDocType().equals(".jpg")) {

                holder.sender_image_card.setVisibility(View.VISIBLE);
                holder.sender_doc_image.setVisibility(View.VISIBLE);
                Picasso.get().load(messages.getMessage()).into(holder.sender_doc_image);

                holder.sender_doc_time.setVisibility(View.VISIBLE);
                holder.sender_doc_time.setText(messages.getTime());

            } else if (messages.getDocType().equals(".pdf")) {

                holder.sender_doc_image.setVisibility(View.GONE);
                holder.sender_message.setVisibility(View.VISIBLE);
                holder.sender_message.setBackgroundResource(R.drawable.sender_message_layout);
                holder.sender_message.setText(messages.getDocName());
                holder.sender_time.setVisibility(View.VISIBLE);
                holder.sender_time.setText(messages.getTime());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(mList.get(position).getMessage()));
                        holder.itemView.getContext().startActivity(intent);
                    }
                });

            } else if (messages.getDocType().equals(".docx")) {

                holder.sender_doc_image.setVisibility(View.GONE);
                holder.sender_message.setVisibility(View.VISIBLE);
                holder.sender_message.setBackgroundResource(R.drawable.sender_message_layout);
                holder.sender_message.setText(messages.getDocName());
                holder.sender_time.setVisibility(View.VISIBLE);
                holder.sender_time.setText(messages.getTime());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(mList.get(position).getMessage()));
                        holder.itemView.getContext().startActivity(intent);
                    }
                });

            } else {


                holder.sender_doc_image.setVisibility(View.GONE);
                holder.sender_message.setVisibility(View.VISIBLE);
                holder.sender_message.setBackgroundResource(R.drawable.sender_message_layout);
                holder.sender_message.setText(messages.getMessage());
                holder.sender_time.setVisibility(View.VISIBLE);
                holder.sender_time.setText(messages.getTime());

            }

        } else {

            switch (messages.getDocType()) {
                case ".jpg":

                    holder.receiver_image_card.setVisibility(View.VISIBLE);
                    holder.receiver_message.setVisibility(View.INVISIBLE);
                    holder.receiver_doc_image.setVisibility(View.VISIBLE);
                    Picasso.get().load(messages.getMessage()).into(holder.receiver_doc_image);

                    holder.receiver_name.setVisibility(View.VISIBLE);
                    holder.receiver_name.setText(messages.getName());

                    holder.receiver_sector.setVisibility(View.VISIBLE);
                    holder.receiver_sector.setText("| " + messages.getSector());

                    holder.receiverProfileImage.setVisibility(View.VISIBLE);
                    holder.cardView.setVisibility(View.VISIBLE);
                    Picasso.get().load(messages.getImage()).placeholder(R.drawable.astronaut).into(holder.receiverProfileImage);


                    holder.receiver_doc_time.setVisibility(View.VISIBLE);
                    holder.receiver_doc_time.setText(messages.getTime());

                    break;
                case ".pdf":

                    holder.receiver_name.setVisibility(View.VISIBLE);
                    holder.receiver_name.setText(messages.getName());

                    holder.receiver_sector.setVisibility(View.VISIBLE);
                    holder.receiver_sector.setText("| " + messages.getSector());

                    holder.receiverProfileImage.setVisibility(View.VISIBLE);
                    holder.cardView.setVisibility(View.VISIBLE);
                    Picasso.get().load(messages.getImage()).placeholder(R.drawable.astronaut).into(holder.receiverProfileImage);

                    holder.receiver_message.setVisibility(View.VISIBLE);
                    holder.receiver_message.setText(messages.getDocName());
                    holder.receiver_time.setText(messages.getTime());

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(mList.get(position).getMessage()));
                            holder.itemView.getContext().startActivity(intent);
                        }
                    });

                    break;
                case ".docx":

                    holder.receiver_name.setVisibility(View.VISIBLE);
                    holder.receiver_name.setText(messages.getName());

                    holder.receiver_sector.setVisibility(View.VISIBLE);
                    holder.receiver_sector.setText("| " + messages.getSector());

                    holder.receiverProfileImage.setVisibility(View.VISIBLE);
                    Picasso.get().load(messages.getImage()).placeholder(R.drawable.astronaut).into(holder.receiverProfileImage);

                    holder.cardView.setVisibility(View.VISIBLE);
                    holder.receiver_message.setVisibility(View.VISIBLE);
                    holder.receiver_message.setText(messages.getDocName());

                    holder.receiver_time.setText(messages.getTime());

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(mList.get(position).getMessage()));
                            holder.itemView.getContext().startActivity(intent);
                        }
                    });
                    break;
                default:

                    holder.receiver_name.setVisibility(View.VISIBLE);
                    holder.receiver_name.setText(messages.getName());

                    holder.receiver_sector.setVisibility(View.VISIBLE);
                    holder.receiver_sector.setText("| " + messages.getSector());

                    holder.receiverProfileImage.setVisibility(View.VISIBLE);
                    holder.receiver_message.setVisibility(View.VISIBLE);
                    holder.cardView.setVisibility(View.VISIBLE);

                    holder.receiver_time.setText(messages.getTime());

                    holder.receiver_message.setBackgroundResource(R.drawable.reciever_message_layout);
                    holder.receiver_message.setText(messages.getMessage());
                    Picasso.get().load(messages.getImage()).placeholder(R.drawable.astronaut).into(holder.receiverProfileImage);

                    if (!messages.getChatName().equals("")) {

                        holder.receiverProfileImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(holder.itemView.getContext(), PrivateChatActivity.class);
                                intent.putExtra("visit_user_id", messages.getId());
                                intent.putExtra("visit_user_name", messages.getName());

                                if (messages.getImage() != null) {
                                    intent.putExtra("visit_user_image", messages.getImage());
                                }

                                intent.putExtra("visit_user_sector", messages.getSector());
                                holder.itemView.getContext().startActivity(intent);
                            }
                        });
                    }
                    break;
            }
        }

        if (checkIfContinuedMessages != null) {
            if (checkIfContinuedMessages.equals(prevMessage)) {
                holder.receiver_name.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.GONE);
                holder.receiverProfileImage.setVisibility(View.GONE);
                holder.sender_doc_time.setVisibility(View.GONE);
                holder.receiver_sector.setVisibility(View.GONE);
                holder.sender_doc_image.setVisibility(View.GONE);
                holder.sender_message.setVisibility(View.GONE);
                holder.receiver_message.setVisibility(View.GONE);
                holder.receiver_message_continuous.setVisibility(View.VISIBLE);
                holder.receiver_message_continuous.setText(messages.getMessage());
            }
        }

        holder.receiver_message_continuous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.receiver_time_continuous.getVisibility() == View.VISIBLE) {
                    holder.receiver_time_continuous.setVisibility(View.GONE);
                } else {
                    holder.receiver_time_continuous.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.receiver_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.receiver_time.getVisibility() == View.GONE) {
                    holder.receiver_time.setVisibility(View.VISIBLE);
                } else {
                    holder.receiver_time.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView sender_message, receiver_message, receiver_name, receiver_sector,
                receiver_time, receiver_time_continuous, sender_time, receiver_doc_time, receiver_message_continuous, sender_doc_time;
        ImageView receiverProfileImage, sender_doc_image, receiver_doc_image;
        CardView cardView, sender_image_card, receiver_image_card;

        MyHolder(@NonNull View itemView) {
            super(itemView);

            sender_message = itemView.findViewById(R.id.sender_message_text);
            receiver_message = itemView.findViewById(R.id.reciever_message_text);
            receiver_message_continuous = itemView.findViewById(R.id.reciever_message_text_continuous);
            receiverProfileImage = itemView.findViewById(R.id.message_profile_image);
            cardView = itemView.findViewById(R.id.message_profile_image_card);
            receiver_name = itemView.findViewById(R.id.reciever_name);
            receiver_sector = itemView.findViewById(R.id.reciever_sector);
            receiver_time = itemView.findViewById(R.id.reciever_time);
            receiver_time_continuous = itemView.findViewById(R.id.reciever_time_continuous);
            sender_time = itemView.findViewById(R.id.sender_time);
            sender_doc_image = itemView.findViewById(R.id.sender_doc);
            receiver_doc_image = itemView.findViewById(R.id.reciever_doc);
            receiver_doc_time = itemView.findViewById(R.id.reciever_doc_time);
            sender_doc_time = itemView.findViewById(R.id.sender_doc_time);
            sender_image_card = itemView.findViewById(R.id.sender_doc_card);
            receiver_image_card = itemView.findViewById(R.id.receiver_doc_card);
        }
    }
}
