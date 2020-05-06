package com.example.codeacademyapp.ui.main.sector.task.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.codeacademyapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserStatsDetailsFragment extends Fragment {

    private String userName, imageUrl;
    private int completedTasksCount, personalTasksCount;
    private double completionRate3;
    private TextView userEfficiency;
    private CircleImageView userImg;


    public UserStatsDetailsFragment(String imageUrl, String userName, int completedTasksCount, int personalTasksCount) {
        this.userName = userName;
        this.completedTasksCount = completedTasksCount;
        this.personalTasksCount = personalTasksCount;
        this.imageUrl = imageUrl;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_stats_details, container, false);

        PieChart chart = view.findViewById(R.id.tasks_stats_chart);
        TextView userNameTV = view.findViewById(R.id.user_name_stats_details);
        ImageView pieImg = view.findViewById(R.id.pie_chart_img);
        TextView textUnderImg = view.findViewById(R.id.text_below_pieChartImg);
        userEfficiency = view.findViewById(R.id.user_efficiency_tv);
        userNameTV.setText(userName);
        userImg = view.findViewById(R.id.users_profile_image_stats_details);

        Picasso.get().load(imageUrl)
                .placeholder(R.drawable.astronaut)
                .into(userImg);

        if (completedTasksCount != 0 && personalTasksCount != 0) {
            double allTasks = completedTasksCount + personalTasksCount;
            double completionRate1 = (completedTasksCount / allTasks);
            double completionRate2 = completionRate1 * 100;
            completionRate3 = (int) completionRate2;
        }

        if (completionRate3 <= 0.00) {
            userEfficiency.setVisibility(View.GONE);
        } else if (completionRate3 <= 20.00) {
            userEfficiency.setText(String.format(getString(R.string.efficiency_very_low), userName));
        } else if (completionRate3 >= 20.00 && completionRate3 <= 40.00) {
            userEfficiency.setText(String.format(getString(R.string.efficiecy_low), userName));
        } else if (completionRate3 >= 40.00 && completionRate3 <= 60.00) {
            userEfficiency.setText(String.format(getString(R.string.efficiency_normal), userName));
        } else if (completionRate3 >= 60.00 && completionRate3 <= 80.00) {
            userEfficiency.setText(String.format(getString(R.string.efficiency_very_good), userName));
        } else {
            userEfficiency.setText(String.format(getString(R.string.efficiency_excellent), userName));
        }

        List<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(completedTasksCount, getString(R.string.copleted_tasks)));
        data.add(new PieEntry(personalTasksCount, getString(R.string.todo_tasks)));

        PieDataSet dataSet = new PieDataSet(data, "");
        dataSet.setValueLineColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.AccentColor));
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.AccentColor));
        dataSet.setColors(ContextCompat.getColor(getContext(), R.color.AccentColor),
                ContextCompat.getColor(getContext(), R.color.LogoMainColor));

        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(18f);

        chart.setCenterText(completionRate3 + "%");
        chart.setCenterTextSize(10f);
        chart.getDescription().setEnabled(false);
        chart.setHoleRadius(45f);
        chart.setHoleColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        chart.setCenterTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDarkMode));
        chart.setCenterTextSize(18);
        chart.setTransparentCircleRadius(50f);
        chart.setEntryLabelColor(ContextCompat.getColor(getContext(), R.color.black));
        chart.setEntryLabelTextSize(11);
        PieData pieData = new PieData(dataSet);
        if (completedTasksCount != 0 && personalTasksCount != 0) {
            chart.setData(pieData);
        } else {
            chart.setVisibility(View.GONE);
            pieImg.setVisibility(View.VISIBLE);
            textUnderImg.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
