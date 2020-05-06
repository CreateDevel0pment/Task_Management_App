package com.example.codeacademyapp.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.edit_find.edit.EditProfileActivity;
import com.example.codeacademyapp.ui.main.edit_find.find_friends.FindFriendsActivity;
import com.example.codeacademyapp.ui.main.home.HomeFragment;
import com.example.codeacademyapp.ui.main.home.HomeSettings;
import com.example.codeacademyapp.ui.main.sector.chat.GroupChatFragment;
import com.example.codeacademyapp.ui.main.sector.task.TaskActivity;
import com.example.codeacademyapp.ui.main.sector.task.fragment.TaskTabsFragment;
import com.example.codeacademyapp.ui.main.wall.WallTabFragment;
import com.example.codeacademyapp.ui.sign_in_up.StartActivity;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.example.codeacademyapp.utils.NetworkConnectivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    Toolbar toolbar;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        bottomNav = findViewById(R.id.bottom_bar);
//        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.home:
//                        switchToFragment(new HomeFragment(), R.id.home_container);
//                        break;
//                    case R.id.walle:
//                        switchToFragment(new WallTabFragment(), R.id.wall_container);
//                        break;
//                    case R.id.sector:
//                        switchToFragment(new GroupChatFragment(), R.id.group_container);
//                        break;
//                    case R.id.tasks_b_v:
//                        switchToFragment(new TaskTabsFragment(), R.id.task_container);
//                        break;
//                }
//                return true;
//            }
//        });
//
//        bottomNav.setSelectedItemId(R.id.home);
//    }



//    public void switchToFragment(BaseFragment fragment, int container) {
//        Fragment topFragment = getSupportFragmentManager().findFragmentById(container);
//        if (topFragment==fragment) {
//            fragment = new BaseFragment() {
//            };
//        }
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction()
//                .replace(container, fragment)
//                .commit();
//
//        FrameLayout home = findViewById(R.id.home_container);
//        home.setVisibility(View.GONE);
//        FrameLayout wall = findViewById(R.id.wall_container);
//        wall.setVisibility(View.GONE);
//        FrameLayout group = findViewById(R.id.group_container);
//        group.setVisibility(View.GONE);
//        FrameLayout task = findViewById(R.id.task_container);
//        task.setVisibility(View.GONE);
//        findViewById(container).setVisibility(View.VISIBLE);
//    }

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
                break;
            case R.id.find_friends_options:
                Intent intentFindFriends = new Intent(MainActivity.this, FindFriendsActivity.class);
                startActivity(intentFindFriends);
                break;
            case R.id.edit_profile:
                Intent intentEditProfile = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivity(intentEditProfile);
                break;
            case R.id.edit_home:
                Intent intentSettingsHome = new Intent(MainActivity.this, HomeSettings.class);
                startActivity(intentSettingsHome);
            default:
                return false;
        }
        return true;
    }

    private void logOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        finish();
        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
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
