package com.example.codeacademyapp.ui.main.scheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.UserModelFirebase;
import com.example.codeacademyapp.databinding.ItemWorkdayForCreatorBinding;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkDayCreatorAdapter extends RecyclerView.Adapter<WorkDayCreatorAdapter.WorkDayCreatorViewHolder> {
    private List<Workday> workdays;
    private Context context;
    private WorkdayViewModel workdayViewModel;
    private String shift;
    private CreateScheduleFragment createScheduleFragment;
    private FragmentManager fm;

    public WorkDayCreatorAdapter(List<Workday> workdays, Context context, WorkdayViewModel workdayViewModel, CreateScheduleFragment createScheduleFragment, FragmentManager fragmentManager) {
        this.workdays = workdays;
        this.context = context;
        this.workdayViewModel = workdayViewModel;
        this.createScheduleFragment = createScheduleFragment;
        this.fm = fragmentManager;
    }

    @NonNull
    @Override
    public WorkDayCreatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWorkdayForCreatorBinding binding = ItemWorkdayForCreatorBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new WorkDayCreatorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkDayCreatorViewHolder holder, int position) {
        Workday workday = workdays.get(holder.getAdapterPosition());

        final List<UserModelFirebase> firstShift = new ArrayList<>();
        final List<UserModelFirebase> secondShift = new ArrayList<>();

        workdayViewModel.getSchedule(workday).observe(createScheduleFragment, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot scheduleDataSnapshot : dataSnapshot.child("First shift").getChildren()) {
                        String userIdFirstShift = (Objects.requireNonNull(scheduleDataSnapshot.getValue(UserModelFirebase.class))).id;
                        String userNameFirstShift = (Objects.requireNonNull(scheduleDataSnapshot.getValue(UserModelFirebase.class))).Name;
                        String userImageFirstShift = (Objects.requireNonNull(scheduleDataSnapshot.getValue(UserModelFirebase.class))).image;
                        final UserModelFirebase user = new UserModelFirebase( userNameFirstShift, userImageFirstShift);
                        firstShift.add(user);

                    }
                }
            }
        });

        workdayViewModel.getSchedule(workday).observe(createScheduleFragment, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot scheduleDataSnapshot : dataSnapshot.child("Second shift").getChildren()){
                        String userIdSecondShift = (Objects.requireNonNull(scheduleDataSnapshot.getValue(UserModelFirebase.class))).id;
                        String userNameSecondShift = (Objects.requireNonNull(scheduleDataSnapshot.getValue(UserModelFirebase.class))).Name;
                        String userImageSecondShift = (Objects.requireNonNull(scheduleDataSnapshot.getValue(UserModelFirebase.class))).image;
                        final UserModelFirebase user = new UserModelFirebase(userNameSecondShift, userImageSecondShift);
                        secondShift.add(user);

                    }
                }
            }
        });

        holder.binding.dayTitle.setText(workday.getDate().toString().substring(0,10));

        holder.binding.manageWDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageShiftsFragment shiftsFragment = new ManageShiftsFragment(firstShift, secondShift);
                fm.beginTransaction().replace(R.id.scheduler_container, shiftsFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return workdays.size();
    }

    public class WorkDayCreatorViewHolder extends RecyclerView.ViewHolder {
        ItemWorkdayForCreatorBinding binding;
        public WorkDayCreatorViewHolder(@NonNull ItemWorkdayForCreatorBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            this.binding = itemViewBinding;
        }
    }
}
