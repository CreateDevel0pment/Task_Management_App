package com.example.codeacademyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codeacademyapp.ui.academy_wall.AcademyWallFragment;
import com.example.codeacademyapp.ui.group.GroupFragment;
import com.example.codeacademyapp.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.codeacademyapp.utils.Constants.USER;

public class StartActivity extends AppCompatActivity {

    TextView log_out_view;
    private FirebaseAuth auth;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Intent intent=getIntent();
//        String name = intent.getStringExtra(USER);
        Toast toast=Toast.makeText(this,"WELCOME",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

        auth=FirebaseAuth.getInstance();

        log_out_view=findViewById(R.id.log_out_view);
        log_out_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signOut();
            }
        });

        bottomNav = findViewById(R.id.bottom_bar);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        switchToFragment(new HomeFragment());
                        break;
                    case R.id.academy_wall:
                        switchToFragment(new AcademyWallFragment());
                        break;
                    case R.id.group:
                        switchToFragment(new GroupFragment());
                        break;
                }
                return false;
            }
        });
        bottomNav.setSelectedItemId(R.id.home);
    }

    public void switchToFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main_container, fragment).commit();
    }

    private void signOut(){
        auth.signOut();
        finish();
        Intent intent=new Intent(StartActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}
