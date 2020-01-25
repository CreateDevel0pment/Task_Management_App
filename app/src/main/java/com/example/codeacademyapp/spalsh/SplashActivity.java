package com.example.codeacademyapp.spalsh;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.sign_in.SignUpActivity;
import com.example.codeacademyapp.StartActivity;
import com.google.firebase.auth.FirebaseUser;

import static com.example.codeacademyapp.utils.Constants.USER;

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
                    goToStartActivity(firebaseUser.getDisplayName());
                } else {
                    goToSignUpActivity();
                }
            }
        });

    }

    private void VerifyUserExistance() {
    }

    private void goToSignUpActivity() {
        Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("SIGNUP","signUp");
        startActivity(intent);
    }

    private void goToStartActivity(String name) {
        Intent intent = new Intent(SplashActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(USER,name);
        startActivity(intent);
    }
}
