package com.example.codeacademyapp.ui.main.sector.task.fragment;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserStatsDetailsFragment extends Fragment {

    private String userName;
    private int completedTasksCount, personalTasksCount;
    private double completionRate3;


    public UserStatsDetailsFragment(String userName, int completedTasksCount, int personalTasksCount) {
        this.userName = userName;
        this.completedTasksCount = completedTasksCount;
        this.personalTasksCount = personalTasksCount;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_stats_details, container, false);

        PieChart chart = view.findViewById(R.id.tasks_stats_chart);
        TextView userNameTV = view.findViewById(R.id.user_name_stats_details);
        ImageView pieImg = view.findViewById(R.id.pie_chart_img);
        TextView textUnderImg = view.findViewById(R.id.text_below_pieChartImg);
        userNameTV.setText(userName);

        if(completedTasksCount!=0&&personalTasksCount!=0){
        double allTasks = completedTasksCount+personalTasksCount;
            double completionRate1 = (completedTasksCount / allTasks);
            double completionRate2 = completionRate1 * 100;
            completionRate3 = (int) completionRate2;
        }

        List<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(completedTasksCount, "Number of completed Tasks"));
        data.add(new PieEntry(personalTasksCount, "Number of TODO Tasks"));

        PieDataSet dataSet = new PieDataSet(data,  "");
        dataSet.setValueLineColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.AccentColor));
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.AccentColor));
        dataSet.setColors(ContextCompat.getColor(getContext(), R.color.AccentColor),
                ContextCompat.getColor(getContext(), R.color.LogoMainColor));

        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(18f);

        chart.setCenterText(completionRate3 +"%");
        chart.setCenterTextSize(10f);
        chart.getDescription().setEnabled(false);
        chart.setHoleRadius(45f);
        chart.setHoleColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        chart.setCenterTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDarkMode));
        chart.setCenterTextSize(18);
        chart.setTransparentCircleRadius(50f);
        chart.setEntryLabelColor(ContextCompat.getColor(getContext(), R.color.black));
        chart.setEntryLabelTextSize(16);
        PieData pieData = new PieData(dataSet);
        if(completedTasksCount!=0&&personalTasksCount!=0){
            chart.setData(pieData);
        } else {
            chart.setVisibility(View.GONE);
            pieImg.setVisibility(View.VISIBLE);
            textUnderImg.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
