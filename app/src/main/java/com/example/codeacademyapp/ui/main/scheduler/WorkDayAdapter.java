package com.example.codeacademyapp.ui.main.scheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.databinding.ItemWorkdayForEmployeeBinding;
import com.google.firebase.database.DataSnapshot;

import java.util.List;


public class WorkDayAdapter extends RecyclerView.Adapter<WorkDayAdapter.ShiftViewHolder> {
    private List<Workday> workdays;
    private Context context;
    private WorkdayViewModel workdayViewModel;
    private String shift;

    private GetScheduleFragment getScheduleFragment;

    WorkDayAdapter(List<Workday> workdays, Context context, WorkdayViewModel workdayViewModel, GetScheduleFragment getScheduleFragment) {
        this.workdays = workdays;
        this.context = context;
        this.workdayViewModel = workdayViewModel;
        this.getScheduleFragment = getScheduleFragment;
    }

    @NonNull
    @Override
    public WorkDayAdapter.ShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWorkdayForEmployeeBinding itemWorkdayForEmployeeBinding = ItemWorkdayForEmployeeBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ShiftViewHolder(itemWorkdayForEmployeeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final WorkDayAdapter.ShiftViewHolder holder, int position) {

        final Workday workday = workdays.get(holder.getAdapterPosition());

        holder.shiftCreatorBinding.interactionImg.setImageResource(R.drawable.date_picker_icon);
        holder.shiftCreatorBinding.assignedToWorkInfo
                .setText("You are not assigned to work this date. To request a working shift click on the calendar icon.");

        workdayViewModel.checkWorkDay(workday).observe(getScheduleFragment, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    holder.shiftCreatorBinding.assignedToWorkInfo
                            .setText("You are assigned to work:");
                    workday.setShift(dataSnapshot.getKey());
                    holder.shiftCreatorBinding.interactionImg.setImageResource(R.drawable.ic_feedback);
                    if (dataSnapshot.getValue().equals("First shift")) {
                        holder.shiftCreatorBinding.assignedShift.setText(dataSnapshot.getValue().toString());
                        holder.shiftCreatorBinding.assignedShift.setTextColor(context.getResources().getColor(R.color.good_green));
                        holder.shiftCreatorBinding.circleCardIndicator.setBackgroundColor(context.getResources().getColor(R.color.good_green));


                    } else if (dataSnapshot.getValue().equals("Second shift")) {
                        holder.shiftCreatorBinding.assignedShift.setText(dataSnapshot.getValue().toString());
                        holder.shiftCreatorBinding.assignedShift.setTextColor(context.getResources().getColor(R.color.purple));
                        holder.shiftCreatorBinding.circleCardIndicator.setBackgroundColor(context.getResources().getColor(R.color.purple));
                    }
                }
            }
        });

        if (workday.getDay() != null) {
            holder.shiftCreatorBinding.dayTitle.setText(String.format("%s%s", workday.getDay(), workday.getDate().toString().substring(0, 10)));
        } else {
            holder.shiftCreatorBinding.dayTitle.setText(workday.getDate().toString().substring(0, 10));
        }


        holder.shiftCreatorBinding.interactionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.shiftCreatorBinding.LLWDRequestShift.setVisibility(View.VISIBLE);
                holder.shiftCreatorBinding.requestShiftBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.shiftCreatorBinding.shiftFirstRB.isChecked()) {
                            shift = "First shift";
                        }
                        if (holder.shiftCreatorBinding.shiftSecondRB.isChecked()) {
                            shift = "Second shift";
                        }
                        holder.shiftCreatorBinding.LLWDRequestShift.setVisibility(View.GONE);
                        if (shift != null) {
                            workday.setShift(shift);
                            workdayViewModel.createWorkday(workday);
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return workdays.size();
    }


    public class ShiftViewHolder extends RecyclerView.ViewHolder {
        ItemWorkdayForEmployeeBinding shiftCreatorBinding;

        public ShiftViewHolder(@NonNull ItemWorkdayForEmployeeBinding itemShiftBinding) {
            super(itemShiftBinding.getRoot());
            this.shiftCreatorBinding = itemShiftBinding;
        }
    }

}
