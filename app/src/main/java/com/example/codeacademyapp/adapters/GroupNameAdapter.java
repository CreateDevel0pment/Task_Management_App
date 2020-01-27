package com.example.codeacademyapp.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.group.ChatFragment;

import java.util.List;

public class GroupNameAdapter extends RecyclerView.Adapter<GroupNameAdapter.MyHoder> {

    List<String> groupNameList;
    FragmentManager fragmentManager;


    public GroupNameAdapter(List<String> groupNameList, FragmentManager fragmentManager) {
        this.groupNameList = groupNameList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public GroupNameAdapter.MyHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_name_item, parent, false);

        return new MyHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupNameAdapter.MyHoder holder, int position) {

        holder.textView.setText(groupNameList.get(holder.getAdapterPosition()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment= new ChatFragment();
                Bundle bundle= new Bundle();
                bundle.putString("TITLE",holder.textView.getText().toString());
                fragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.group_container, fragment)
                        .addToBackStack(ChatFragment.TAG)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupNameList.size();
    }

    public class MyHoder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyHoder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.group_name_text);
        }
    }

}
