package com.example.codeacademyapp.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.sector.task.TaskActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProbaMainActvivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseReference;
    FloatingActionButton fab;
    CardView statisticBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proba_main_activity);
        fab=findViewById(R.id.new_task_button);
        statisticBtn=findViewById(R.id.statistic_);

        checkUserPosition();
    }

    public void checkUserPosition(){

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue()!=null) {
                    String userPosition = dataSnapshot.child("Position").getValue().toString();
                    if (userPosition.equals("Staff")) {
                        fab.setVisibility(View.INVISIBLE);
                        statisticBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                AlertDialog alertDialog= new AlertDialog.Builder(ProbaMainActvivity.this)
                                        .setTitle("Restriction alert !")
                                        .setMessage("\n For position Staff,\n not allowed to view statistic.")
                                        .create();
                                alertDialog.show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
