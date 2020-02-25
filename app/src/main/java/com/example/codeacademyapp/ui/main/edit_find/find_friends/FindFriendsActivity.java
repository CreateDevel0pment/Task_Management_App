package com.example.codeacademyapp.ui.main.edit_find.find_friends;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.codeacademyapp.R;

import java.util.Objects;

public class FindFriendsActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        toolbar = findViewById(R.id.toolbar_find_friends);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Employ");
        layout = findViewById(R.id.user_byId_container);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.user_byId_container, new AllFriendsListFragment())
                .commit();
    }

}
