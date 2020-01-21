package com.example.codeacademyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    TextView log_out_view;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        auth=FirebaseAuth.getInstance();

        log_out_view=findViewById(R.id.log_out_view);

        log_out_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
                Intent intent=new Intent(StartActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });




    }

}
