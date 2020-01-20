package com.example.codeacademyapp.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codeacademyapp.MainActivity;
import com.example.codeacademyapp.R;
import com.example.codeacademyapp.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

   private EditText mail_et, password_et;
   private Button log_in_btn;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_sign_in, container, false);

        mail_et=view.findViewById(R.id.mail_text);
        password_et=view.findViewById(R.id.password_text);
        log_in_btn=view.findViewById(R.id.log_in_btn);

        auth = FirebaseAuth.getInstance();

        log_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail_et.getText().toString();
                String password = password_et.getText().toString();

                if(!email.equals("") && !password.equals("")){
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                Intent intent = new Intent(getContext(), StartActivity.class);
                                startActivity(intent);
                            }
                            else {

                                Toast.makeText(getContext(), "Please Sign Up", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {

                    Toast.makeText(getContext(), "Please fill all the fields",
                                Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
