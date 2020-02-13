package com.example.codeacademyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.CompletedBy;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.ui.main.sector.task.TaskViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskInformation> tasks;
    private Context context;
    private FragmentManager fragmentManager;
    String userId;
    String completedCheck;

    private TaskViewModel taskViewModel;
    List<CompletedBy> completedByList, localCompletedByList;
    private DatabaseReference myRef;


    public TaskAdapter(Context context, List<TaskInformation> tasks, FragmentManager fragmentManager, String userId, TaskViewModel taskViewModel, String completedCheck) {
        this.tasks = tasks;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.userId = userId;
        this.taskViewModel = taskViewModel;
        this.completedCheck = completedCheck;
    }



    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskAdapter.TaskViewHolder holder, final int position) {
        final TaskInformation task = tasks.get(holder.getAdapterPosition());

        myRef = FirebaseDatabase.getInstance().getReference().child("Tasks").child(task.getName());
        completedByList = new ArrayList<>();
        localCompletedByList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                localCompletedByList = dataSnapshot.getValue(TaskInformation.class).getCompletedBy();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        holder.name.setText(task.getName());
        holder.priority.setText(task.getTaskPriority());
        holder.description.setText(task.getDescription());
        holder.note.setText(task.getNote());
        holder.timeCreated.setText(task.getTimeCreated());
        holder.taskDeadline.setText(task.getEndDate());

        holder.detailsDropdownIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!completedCheck.equals("completeGONE")){
                    holder.completeTask.setVisibility(View.VISIBLE);}
                holder.priorityLinear.setVisibility(View.VISIBLE);
                holder.descLinear.setVisibility(View.VISIBLE);
                holder.noteLinear.setVisibility(View.VISIBLE);
                holder.datesLinear.setVisibility(View.VISIBLE);
                holder.detailsUpIc.setVisibility(View.VISIBLE);
                holder.detailsDropdownIc.setVisibility(View.GONE);
            }
        });

        holder.detailsUpIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.completeTask.setVisibility(View.GONE);
                holder.priorityLinear.setVisibility(View.GONE);
                holder.descLinear.setVisibility(View.GONE);
                holder.noteLinear.setVisibility(View.GONE);
                holder.datesLinear.setVisibility(View.GONE);
                holder.detailsUpIc.setVisibility(View.GONE);
                holder.detailsDropdownIc.setVisibility(View.VISIBLE);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!completedCheck.equals("completeGONE")){
                    holder.completeTask.setVisibility(View.VISIBLE);}
                holder.completeTask.setVisibility(View.VISIBLE);
                holder.priorityLinear.setVisibility(View.VISIBLE);
                holder.descLinear.setVisibility(View.VISIBLE);
                holder.noteLinear.setVisibility(View.VISIBLE);
                holder.datesLinear.setVisibility(View.VISIBLE);
                holder.detailsUpIc.setVisibility(View.VISIBLE);
                holder.detailsDropdownIc.setVisibility(View.GONE);
            }
        });

        holder.completeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletedBy completedByUser = new CompletedBy(userId);

                if(localCompletedByList==null){
                    completedByList.add(completedByUser);
                    task.setCompletedBy(completedByList);
                } else {
                    localCompletedByList.add(completedByUser);
                    task.setCompletedBy(localCompletedByList);
                }

               taskViewModel.addCompletedBy(task);

                tasks.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyDataSetChanged();

                Toast.makeText(context, "Task completed", Toast.LENGTH_SHORT).show();

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

        TextView description, note, name, priority, timeCreated, taskDeadline;
        CardView cardView, completeTask;
        ImageView detailsDropdownIc, detailsUpIc;
        LinearLayout descLinear, noteLinear, datesLinear, priorityLinear;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_item_task);
            priority = itemView.findViewById(R.id.task_priority);
            name = itemView.findViewById(R.id.task_name_item);
            description = itemView.findViewById(R.id.task_desc_details);
            note = itemView.findViewById(R.id.task_note_details);
            timeCreated = itemView.findViewById(R.id.task_dateCreated_details);
            taskDeadline = itemView.findViewById(R.id.task_endDate_details);
            detailsDropdownIc = itemView.findViewById(R.id.details_task_dropdown_btn_img);
            detailsUpIc = itemView.findViewById(R.id.details_task_dropUp_btn_img);
            descLinear = itemView.findViewById(R.id.desc_linear);
            noteLinear = itemView.findViewById(R.id.note_linear);
            datesLinear = itemView.findViewById(R.id.dates_linear);
            priorityLinear = itemView.findViewById(R.id.priority_linear);
            completeTask = itemView.findViewById(R.id.task_complete);

        }
    }

}
