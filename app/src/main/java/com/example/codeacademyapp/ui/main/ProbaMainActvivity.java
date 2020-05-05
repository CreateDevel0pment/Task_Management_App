package com.example.codeacademyapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.sector.task.TaskActivity;

public class ProbaMainActvivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proba_main_activity);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.home_:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("MAIN", "home");
                startActivity(intent);
                break;
            case R.id.calendar_:
                Intent intent1 = new Intent(this, MainActivity.class);
                intent1.putExtra("MAIN", "calendar");
                startActivity(intent1);
                break;
            case R.id.sector_:
                Intent intent2 = new Intent(this, MainActivity.class);
                intent2.putExtra("MAIN", "sector");
                startActivity(intent2);
                break;
            case R.id.task_:
                Intent intent3 = new Intent(this, MainActivity.class);
                intent3.putExtra("MAIN", "task");
                startActivity(intent3);
                break;
            case R.id.statistic_:
                Intent intent4 = new Intent(this, MainActivity.class);
                intent4.putExtra("MAIN", "statistic");
                startActivity(intent4);
                break;
            case R.id.web_:
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
