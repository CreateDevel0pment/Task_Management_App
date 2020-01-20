package com.example.codeacademyapp.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.codeacademyapp.MainActivity;
import com.example.codeacademyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private EditText name_et, surename_et;
    private EditText mail_et, password_et;
    private Spinner position_spiner, group_spiner;
    private Button sign_up_btn;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    private String position_string;
    private String group_string;

    private static final String TAG = "StartActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);


        name_et=view.findViewById(R.id.person_name);
        surename_et=view.findViewById(R.id.person_sure_name);
        sign_up_btn=view.findViewById(R.id.sign_up_btn);
        mail_et=view.findViewById(R.id.person_email);
        password_et=view.findViewById(R.id.person_password);

        position_spiner=view.findViewById(R.id.spinner_role);
        group_spiner=view.findViewById(R.id.spinner_group);


        position_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position_spiner.getSelectedItem().equals("HR")){
                    position_string="HR";
                }
                else if(position_spiner.getSelectedItem().equals("Tutor")){
                    position_string="Tutor";
                }
                else if(position_spiner.getSelectedItem().equals("Student")){
                    position_string="Student";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        group_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(group_spiner.getSelectedItem().equals("Android")){
                    group_string="Android";
                }
                else if(group_spiner.getSelectedItem().equals("Web Development")){
                    group_string="Web Development";
                }
                else if(group_spiner.getSelectedItem().equals("Ruby on rails")){
                    group_string="Ruby on rails";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    // User is signed in

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    toastMessage("Successfully signed in with: " + user.getEmail());

                } else {

                    // User is signed out

                    Log.d(TAG, "onAuthStateChanged:signed_out");

                    toastMessage("Successfully signed out.");

                }

                // ...

            }

        };

        // Read from the database

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again

                // whenever data at this location is updated.

                Object value = dataSnapshot.getValue();

                Log.d(TAG, "Value is: " + value);
            }
            @Override

            public void onCancelled(DatabaseError error) {

                // Failed to read value

                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = mail_et.getText().toString();
                String password = password_et.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

                    Toast.makeText(getContext(), "Please fill all the fields",
                            Toast.LENGTH_SHORT).show();
                } else {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener((Activity) getContext(),
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {

                                                Toast.makeText(getContext(),
                                                        "Successful Registered",
                                                        Toast.LENGTH_SHORT).show();

                                                startActivity(new Intent(getContext(), MainActivity.class));
                                            } else {

                                                Toast.makeText(getContext(),
                                                        "User name may already exist", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                    password_et.setText("");
                }


                Log.d(TAG, "onClick: Attempting to add object to database.");

                String name=name_et.getText().toString();
                String surename=surename_et.getText().toString();

                if(!name.equals("") || !surename.equals("")){

                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();

                    myRef.child(userID).child("Profile Information").child("Name").setValue(name);
                    myRef.child(userID).child("Profile Information").child("Surname").setValue(surename);
                    myRef.child(userID).child("Profile Information").child("Group").setValue(group_string);
                    myRef.child(userID).child("Profile Information").child("Position").setValue(position_string);

                    toastMessage("Added new person to database");

                    //reset the text
                    name_et.setText("");
                    surename_et.setText("");
                }
            }

        });

        return view;
    }
    private void toastMessage(String message){

        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
}
