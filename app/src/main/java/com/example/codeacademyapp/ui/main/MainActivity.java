package com.example.codeacademyapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.main.edit_find.edit.EditProfileActivity;
import com.example.codeacademyapp.ui.main.edit_find.find_friends.FindFriendsActivity;
import com.example.codeacademyapp.ui.main.group.chat.ChatViewModel;
import com.example.codeacademyapp.ui.main.group.chat.ChatFragment;
import com.example.codeacademyapp.ui.main.home.HomeFragment;
import com.example.codeacademyapp.ui.main.wall.AcademyWallFragment;
import com.example.codeacademyapp.ui.sign_in_up.SignUpActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.codeacademyapp.utils.Constants.USER;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    Toolbar toolbar;

    ChatViewModel groupChatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String checkingForFragmentInflate = intent.getStringExtra("inflateGroupChat");
        if(checkingForFragmentInflate!= null){

            if(checkingForFragmentInflate.equals("inflateGroupChat")){
                groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

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
                            case R.id.sector:
                                switchToFragment(new ChatFragment(), R.id.group_container);
                                break;
                        }
                        return false;
                    }
                });
                bottomNav.setSelectedItemId(R.id.sector);}

        } else {

            groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

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
                        case R.id.sector:
                            switchToFragment(new ChatFragment(), R.id.group_container);
                            break;
                    }
                    return false;
                }
            });
            bottomNav.setSelectedItemId(R.id.home);
        }


//        groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
//
//        bottomNav = findViewById(R.id.bottom_bar);
//        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.home:
//                        switchToFragment(new HomeFragment(), R.id.home_container);
//                        break;
//                    case R.id.academy_wall:
//                        switchToFragment(new AcademyWallFragment(), R.id.wall_container);
//                        break;
//                    case R.id.sector:
//                        switchToFragment(new ChatFragment(), R.id.group_container);
//                        break;
//                }
//                return false;
//            }
//        });
//        bottomNav.setSelectedItemId(R.id.home);

//        String name = intent.getStringExtra(USER);
//        if (name != null) {
//            toastMessage("Welcome " + name);
//        }



        bottomNav.setSelectedItemId(R.id.home);
    }

    public void switchToFragment(Fragment fragment, int container) {
        Fragment topFragment = getSupportFragmentManager().findFragmentById(container);
        if (topFragment == fragment) {
            fragment = new Fragment();
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
            default:
                return false;
        }
        return true;
    }

    private void logOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        finish();
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private void toastMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
