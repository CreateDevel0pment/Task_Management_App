package com.example.codeacademyapp.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.StartActivity;
import com.example.codeacademyapp.users.model.User;
import com.example.codeacademyapp.users.repository.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.codeacademyapp.utils.HelperTextFocus.setFocus;

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

    private String role_string;
    private String group_string;

    private UserViewModel userViewModel;


    private static final String TAG = "StartActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);

        userViewModel= ViewModelProviders.of(SignUpFragment.this).get(UserViewModel.class);

        name_et=view.findViewById(R.id.person_name);
        setFocus(name_et);
        surename_et=view.findViewById(R.id.person_sure_name);
        setFocus(surename_et);
        sign_up_btn=view.findViewById(R.id.sign_up_btn);
        mail_et=view.findViewById(R.id.person_email);
        setFocus(mail_et);
        password_et=view.findViewById(R.id.person_password);
        setFocus(password_et);

        position_spiner=view.findViewById(R.id.spinner_role);
        group_spiner=view.findViewById(R.id.spinner_group);


        position_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position_spiner.getSelectedItem().equals("HR")){
                    role_string="HR";
                }
                else if(position_spiner.getSelectedItem().equals("Tutor")){
                    role_string="Tutor";
                }
                else if(position_spiner.getSelectedItem().equals("Student")){
                    role_string="Student";
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

                User user=new User();
                user.seteMail(mail_et.getText().toString());
                user.setPassword(password_et.getText().toString());
                user.setName(name_et.getText().toString());
                user.setSurname(surename_et.getText().toString());
                user.setGroup_spinner(group_string);
                user.setRole_spinner(role_string);

                userViewModel.signUpNewUser(user);
                userViewModel.getSignUpNewUserLiveData().observe(SignUpFragment.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {

                        if(user.isCreated){
                            toastMessage("Welcome");
                            goToMainActivity(user);
                        }
                    }
                });
            }

        });

        return view;
    }
    private void toastMessage(String message){

        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void goToMainActivity(User user) {
        Intent intent = new Intent(getContext(), StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra(USER, user.getName());
        startActivity(intent);
//        finish();
    }
}
