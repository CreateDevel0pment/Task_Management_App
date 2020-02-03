package com.example.codeacademyapp.spalsh;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.main.group.GroupChatViewModel;
import com.example.codeacademyapp.main.StartActivity;
import com.example.codeacademyapp.sign_in.SignUpActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashViewModel splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        splashViewModel.checkIfUserIsAuthenticated();

        splashViewModel.getIsUserAuthenticatedLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {

                if (firebaseUser != null) {
                    getReferencesForUserGroup();
                } else {
                    goToSignUpActivity();
                }
            }
        });
    }

    public void getReferencesForUserGroup() {

        GroupChatViewModel groupChatViewModel = ViewModelProviders.of(this).get(GroupChatViewModel.class);
        groupChatViewModel.getUserIngormations().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    String userGroup = Objects.requireNonNull(dataSnapshot.child("Group").getValue()).toString();
                    goToStartActivity(userGroup);
                }
            }
        });
    }

    private void goToSignUpActivity() {
        Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("SIGNUP", "signUp");
        startActivity(intent);
    }

    private void goToStartActivity(String userGroup) {
        Intent intent = new Intent(SplashActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("TITLE", userGroup);
        startActivity(intent);
    }
}
