package com.example.codeacademyapp.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.main.group.GroupFragment;
import com.example.codeacademyapp.main.home.HomeFragment;
import com.example.codeacademyapp.main.wall.AcademyWallFragment;
import com.example.codeacademyapp.sign_in.SignUpActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.codeacademyapp.utils.Constants.USER;

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    BottomNavigationView bottomNav;
    Toolbar toolbar;

    GroupChatViewModel groupChatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String name = intent.getStringExtra(USER);
        if (name != null) {
            toastMessage("Welcome " + name);
        } else {
            toastMessage("Welcome");
        }

        auth = FirebaseAuth.getInstance();
        groupChatViewModel = ViewModelProviders.of(this).get(GroupChatViewModel.class);

        bottomNav = findViewById(R.id.bottom_bar);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        switchToFragment(new HomeFragment(), R.id.home_container);
                        break;
                    case R.id.academy_wall:
                        switchToFragment(new AcademyWallFragment(), R.id.wall_container);
                        break;
                    case R.id.group:
                        switchToFragment(new GroupFragment(), R.id.group_container);
                        break;
                }
                return false;
            }
        });

        bottomNav.setSelectedItemId(R.id.home);
    }

    public void switchToFragment(Fragment fragment, int container) {
        Fragment topFragment = getSupportFragmentManager().findFragmentById(container);
        if (topFragment == fragment) {
            fragment = new Fragment();
            fragment = topFragment;
        }
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(container, fragment)
                .commit();

        FrameLayout home = findViewById(R.id.home_container);
        home.setVisibility(View.GONE);
        FrameLayout wall = findViewById(R.id.wall_container);
        wall.setVisibility(View.GONE);
        FrameLayout group = findViewById(R.id.group_container);
        group.setVisibility(View.GONE);
        findViewById(container).setVisibility(View.VISIBLE);
    }

    private void requestNewGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this, R.style.AlertDialog);
        builder.setTitle("Create Group");

        final EditText groupNameField = new EditText(StartActivity.this);
        groupNameField.setHint("group name");
        groupNameField.setHintTextColor(Color.GRAY);
        groupNameField.setTextColor(Color.BLACK);

        builder.setView(groupNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String groupName = groupNameField.getText().toString();

                if (TextUtils.isEmpty(groupName)) {
                    Toast.makeText(StartActivity.this, "Enter Group Name", Toast.LENGTH_SHORT).show();
                } else {
                    groupChatViewModel.setGroupNameOnFirebase(groupName);
                }
            }
        });
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.start_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.log_out_btn:
                logOut();
            case R.id.find_friends_options:
                break;
            case R.id.main_create_group:
                requestNewGroup();
                break;
            default:
                return false;
        }
        return true;
    }

    private void logOut() {
        auth.signOut();
        finish();
        Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void toastMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
