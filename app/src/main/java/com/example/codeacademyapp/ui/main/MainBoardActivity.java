package com.example.codeacademyapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.sector.task.TaskActivity;

public class MainBoardActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);
    }

    public void onCLick(View view) {
        switch (view.getId()) {

            case R.id.home_card:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("MAIN", "home");
                startActivity(intent);
                break;
            case R.id.calendar_card:
                Intent intent1 = new Intent(this, MainActivity.class);
                intent1.putExtra("MAIN", "calendar");
                startActivity(intent1);
                break;
            case R.id.sector_card:
                Intent intent2 = new Intent(this, MainActivity.class);
                intent2.putExtra("MAIN", "sector");
                startActivity(intent2);
                break;
            case R.id.tasks_card:
                Intent intent3 = new Intent(this, MainActivity.class);
                intent3.putExtra("MAIN", "task");
                startActivity(intent3);
                break;
            case R.id.statistics_card:
                Intent intent4 = new Intent(this, MainActivity.class);
                intent4.putExtra("MAIN", "statistic");
                startActivity(intent4);
                break;
            case R.id.web_card:
                Intent intent5 = new Intent(this, MainActivity.class);
                intent5.putExtra("MAIN", "web");
                startActivity(intent5);
                break;
            case R.id.new_task_button:
                Intent newTaskIntent = new Intent(this, TaskActivity.class);
                newTaskIntent.putExtra("type", "forAll");
                startActivity(newTaskIntent);
                break;
        }
    }
}
