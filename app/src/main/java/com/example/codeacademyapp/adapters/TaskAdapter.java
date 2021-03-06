package com.example.codeacademyapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.data.model.CompletedBy;
import com.example.codeacademyapp.data.model.TaskInformation;
import com.example.codeacademyapp.databinding.ItemSingleTaskBinding;
import com.example.codeacademyapp.ui.main.sector.task.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskInformation> tasks;
    private Context context;
    private String userId;
    private String completedCheck;
    private TaskViewModel taskViewModel;
    private List<CompletedBy> completedByList;

    public TaskAdapter(Context context, List<TaskInformation> tasks, String userId, TaskViewModel taskViewModel, String completedCheck) {
        this.tasks = tasks;
        this.context = context;
        this.userId = userId;
        this.taskViewModel = taskViewModel;
        this.completedCheck = completedCheck;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSingleTaskBinding binding = ItemSingleTaskBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskAdapter.TaskViewHolder holder, final int position) {
        final TaskInformation task = tasks.get(holder.getAdapterPosition());

        completedByList = new ArrayList<>();

        holder.itemSingleTaskBinding.taskNameItem.setText(task.getName());
        holder.itemSingleTaskBinding.taskDescDetails.setText(task.getDescription());
        holder.itemSingleTaskBinding.taskDateCreatedDetails.setText(task.getTimeCreated());
        holder.itemSingleTaskBinding.taskEndDateDetails.setText(task.getEndDate());

        holder.itemSingleTaskBinding.shareTaskText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, task.getName() + "\n" + "\n" + task.getDescription());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                context.startActivity(shareIntent);
            }
        });

        holder.itemSingleTaskBinding.detailsTaskDropdownBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!completedCheck.equals("completeGONE")) {
                    holder.itemSingleTaskBinding.taskComplete.setVisibility(View.VISIBLE);
                    holder.itemSingleTaskBinding.lineSeparator.setVisibility(View.VISIBLE);
                    holder.itemSingleTaskBinding.completeLinear.setVisibility(View.VISIBLE);

//                }
        if(task.getDocPath()!=null){
            holder.itemSingleTaskBinding.downloadAttachmentsLinear.setVisibility(View.VISIBLE);
        }
                holder.itemSingleTaskBinding.descLinear.setVisibility(View.VISIBLE);
                holder.itemSingleTaskBinding.datesLinear.setVisibility(View.VISIBLE);
                holder.itemSingleTaskBinding.detailsTaskDropUpBtnImg.setVisibility(View.VISIBLE);
                holder.itemSingleTaskBinding.detailsTaskDropdownBtnImg.setVisibility(View.GONE);
            }
        });

        holder.itemSingleTaskBinding.detailsTaskDropUpBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemSingleTaskBinding.completeLinear.setVisibility(View.GONE);
                holder.itemSingleTaskBinding.lineSeparator.setVisibility(View.GONE);
                holder.itemSingleTaskBinding.taskComplete.setVisibility(View.GONE);
                holder.itemSingleTaskBinding.downloadAttachmentsLinear.setVisibility(View.GONE);
                holder.itemSingleTaskBinding.descLinear.setVisibility(View.GONE);
                holder.itemSingleTaskBinding.datesLinear.setVisibility(View.GONE);
                holder.itemSingleTaskBinding.detailsTaskDropUpBtnImg.setVisibility(View.GONE);
                holder.itemSingleTaskBinding.detailsTaskDropdownBtnImg.setVisibility(View.VISIBLE);
            }
        });

        holder.itemSingleTaskBinding.taskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletedBy completedByUser = new CompletedBy(userId);

                completedByList.add(completedByUser);
                task.setCompletedBy(completedByList);
                taskViewModel.addCompletedBy(task);

                tasks.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyDataSetChanged();

                Toast.makeText(context, "Task completed", Toast.LENGTH_SHORT).show();

            }
        });

        holder.itemSingleTaskBinding.downloadDocumentTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(tasks.get(holder.getAdapterPosition()).getDocPath()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        ItemSingleTaskBinding itemSingleTaskBinding;

        TaskViewHolder(ItemSingleTaskBinding itemSingleTaskBinding) {
            super(itemSingleTaskBinding.getRoot());
            this.itemSingleTaskBinding = itemSingleTaskBinding;
        }
    }
}
