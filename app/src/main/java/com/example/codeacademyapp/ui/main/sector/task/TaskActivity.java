package com.example.codeacademyapp.ui.main.sector.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.sector.task.fragment.AddNewTaskFragment;
import com.example.codeacademyapp.ui.main.sector.task.fragment.TaskTabsFragment;
import com.example.codeacademyapp.utils.NetworkConnectivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaskActivity extends AppCompatActivity {

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Toolbar toolbar = findViewById(R.id.toolbar_tasks_tab_fragment);
        toolbar.setTitle("Back to group chat");
        toolbar.setTitleTextColor((ContextCompat.getColor(this, R.color.colorPrimary)));

        toolbar.setNavigationIcon(R.drawable.ic_back_button_white);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final String userID = getIntent().getStringExtra("id");

        if(userID!=null){
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Name");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        userName = dataSnapshot.getValue().toString();
                        AddNewTaskFragment addNewTaskFragment = new AddNewTaskFragment(userName, userID);
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction()
                                .replace(R.id.task_fragments_container, addNewTaskFragment)
                                .commit();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            TaskTabsFragment taskTabsFragment = new TaskTabsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.task_fragments_container, taskTabsFragment)
                    .commit();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (NetworkConnectivity.isConnectivityNetworkAvailable(this)) {

            Toast connectivityToast = Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG);
            connectivityToast.setGravity(Gravity.CENTER, 0, 0);
            connectivityToast.show();
        }
    }
}