package com.example.codeacademyapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;

public class WeekDaysAdapter extends RecyclerView.Adapter<WeekDaysAdapter.ItemHolder> {

    private String[] days;

    public WeekDaysAdapter(String[] days) {
        this.days = days;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        holder.dayName.setText(days[position]);
    }

    @Override
    public int getItemCount() {
        return days.length;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView dayName;

        ItemHolder(@NonNull View itemView) {
            super(itemView);

            dayName=itemView.findViewById(R.id.day_name);
        }
    }
}
