package com.example.codeacademyapp.main.group.new_tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.TaskInformation;

import java.util.List;

class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskInformation> tasks;
    private Context context;

    TaskAdapter(Context context, List<TaskInformation> tasks) {
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        final TaskInformation task = tasks.get(holder.getAdapterPosition());

        holder.description.setText(task.getDescription());
        holder.note.setText(task.getNote());
        holder.name.setText(task.getName());
        holder.timeCreated.setText(task.getTimeCreated());


        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView description, note, name, timeCreated;
        CardView cardView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_item_task);

            description = itemView.findViewById(R.id.task_desc_item);
            note = itemView.findViewById(R.id.task_note_item);
            name = itemView.findViewById(R.id.task_name_item);
            timeCreated = itemView.findViewById(R.id.task_timeCreated_item);

        }
    }
}
