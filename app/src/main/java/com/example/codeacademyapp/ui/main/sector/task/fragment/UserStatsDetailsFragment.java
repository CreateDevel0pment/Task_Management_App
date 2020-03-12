package com.example.codeacademyapp.ui.main.sector.task.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChartView;
import com.example.codeacademyapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserStatsDetailsFragment extends Fragment {

    private String userID, userName;
    private int completedTasksCount, personalTasksCount;
    private double completionRate1;
    private double completionRate2;
    private double completionRate3;
    private AnyChartView tasksStatsChart;
    private TextView userNameTV, textUnderImg;
    private List<PieEntry> data;
    private PieChart chart;
    ImageView pieImg;

    public UserStatsDetailsFragment() {
    }

    public UserStatsDetailsFragment(String selectedUserId, String userName, int completedTasksCount, int personalTasksCount) {
        this.userID = selectedUserId;
        this.userName = userName;
        this.completedTasksCount = completedTasksCount;
        this.personalTasksCount = personalTasksCount;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_stats_details, container, false);

        chart = view.findViewById(R.id.tasks_stats_chart);
        userNameTV = view.findViewById(R.id.user_name_stats_details);
        pieImg = view.findViewById(R.id.pie_chart_img);
        textUnderImg = view.findViewById(R.id.text_below_pieChartImg);
        userNameTV.setText(userName);

        if(completedTasksCount!=0&&personalTasksCount!=0){
        double allTasks = completedTasksCount+personalTasksCount;
            completionRate1 = (completedTasksCount/allTasks);
            completionRate2 = completionRate1*100;
            completionRate3 = (int) completionRate2;
        }

        data = new ArrayList<>();
        data.add(new PieEntry(completedTasksCount, "Number of completed Tasks"));
        data.add(new PieEntry(personalTasksCount, "Number of TODO Tasks"));

        PieDataSet dataSet = new PieDataSet(data,  "");
        dataSet.setValueLineColor(ContextCompat.getColor(getContext(), R.color.AccentColor));
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
