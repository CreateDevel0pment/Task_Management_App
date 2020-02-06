package com.example.codeacademyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.ui.main.sector.task.fragment.TaskDetailsFragment;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskInformation> tasks;
    private Context context;
    FragmentManager fragmentManager;

    public TaskAdapter(Context context, List<TaskInformation> tasks, FragmentManager fragmentManager) {
        this.tasks = tasks;
        this.context = context;
        this.fragmentManager = fragmentManager;
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

        holder.name.setText(task.getName());
        holder.priority.setText(task.getTaskPriority());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment(task);
                fragmentManager.beginTransaction()
                        .replace(R.id.task_fragments_container, taskDetailsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        if(task.getTaskPriority().equals("High")){
            holder.priority.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else if(task.getTaskPriority().equals("Medium")){
            holder.priority.setTextColor(ContextCompat.getColor(context, R.color.orange));
        } else {
            holder.priority.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView description, note, name, priority, timeCreated;
        CardView cardView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_item_task);
            priority = itemView.findViewById(R.id.task_priority);
            name = itemView.findViewById(R.id.task_name_item);

        }
    }
}
