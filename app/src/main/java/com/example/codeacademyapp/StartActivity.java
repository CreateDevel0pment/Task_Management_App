package com.example.codeacademyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.codeacademyapp.group.GroupFragment;
import com.example.codeacademyapp.home.HomeFragment;
import com.example.codeacademyapp.sign_in.SignUpActivity;
import com.example.codeacademyapp.wall.AcademyWallFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.codeacademyapp.utils.Constants.USER;

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    BottomNavigationView bottomNav;
    Toolbar toolbar;

    private FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = rootRef.getReference();

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

    private void requestNewGroup() {
        AlertDialog.Builder builder=new AlertDialog.Builder(StartActivity.this,R.style.AlertDialog);
                builder.setTitle("Enter Group Name");

                final EditText groupNameField= new EditText(StartActivity.this);
                groupNameField.setHint("name of the group");
                builder.setView(groupNameField);

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String groupName = groupNameField.getText().toString();

                        if(TextUtils.isEmpty(groupName)){
                            Toast.makeText(StartActivity.this, "Enter Group Name", Toast.LENGTH_SHORT).show();
                        }else {
                            createNewGroup(groupName);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
    }

    //ovoj metod treba da ide vo Repo //TODO
    private void createNewGroup(final String groupName) {

        myRef.child("Groups").child(groupName).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    toastMessage(groupName + " is created successfully");
                }
            }
        });
    }

    public void switchToFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main_container, fragment).commit();
    }

    private void logOut() {
        auth.signOut();
        finish();
        Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
        startActivity(intent);
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
            case R.id.settings_options:
                goToActivityMenu();
                break;
            case R.id.main_create_group:
                requestNewGroup();
                break;
                default:
                    return false;
        }
        return true;
    }

    private void toastMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void goToActivityMenu (){

//        Intent intent=new Intent(StartActivity.this, SettingsActivity.class);
//        startActivity(intent);
    }
}
