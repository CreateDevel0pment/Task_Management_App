package com.example.codeacademyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.users.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.codeacademyapp.utils.Constants.USER;

public class SplashActivity extends AppCompatActivity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashViewModel splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        splashViewModel.checkIfUserIsAuthenticated();
//        splashViewModel.getChildData();

        splashViewModel.getIsUserAuthenticatedLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {

                if (firebaseUser != null) {
                    goToStartActivity();
                } else {
                    goToSugnUpActivity();
                }
            }
        });

//        splashViewModel.getGetChildData().observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//
//                name= user.name;
//            }
//        });
    }

    private void goToSugnUpActivity() {
        Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void goToStartActivity() {
        Intent intent = new Intent(SplashActivity.this, StartActivity.class);
        intent.putExtra(USER,name);
        startActivity(intent);
    }
}
