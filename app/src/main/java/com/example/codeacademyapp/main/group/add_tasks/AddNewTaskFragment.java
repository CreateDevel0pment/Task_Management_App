package com.example.codeacademyapp.main.group.add_tasks;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.UserInformation;
import com.example.codeacademyapp.R;
import com.example.codeacademyapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewTaskFragment extends Fragment {


    private EditText task_name, task_description, task_note;
    private Button create_task;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    FirebaseUser userFb;
    String userID;
    TaskViewModel taskViewModel;
    UserInformation uInfo = new UserInformation();

    public AddNewTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_new_task, container, false);

        task_name = rootView.findViewById(R.id.task_name);
        task_description = rootView.findViewById(R.id.task_desc);
        task_note = rootView.findViewById(R.id.task_note);
        create_task = rootView.findViewById(R.id.create_task_btn);

        taskViewModel = ViewModelProviders.of(AddNewTaskFragment.this).get(TaskViewModel.class);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        userFb = mAuth.getCurrentUser();
        if (userFb != null) {
            userID = userFb.getUid();}

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getUserData(dataSnapshot);
            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {

                // Failed to read value

                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


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





        create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Task task = new Task();
                task.setName(task_name.getText().toString());
                task.setDescription(task_description.getText().toString());
                task.setNote(task_note.getText().toString());
                task.setGroup(uInfo.getGroup());

                taskViewModel.createNewTask(task);
                taskViewModel.getCreateNewTaskLiveData().observe(AddNewTaskFragment.this, new Observer<Task>() {
                    @Override
                    public void onChanged(Task task) {
                        if (task.isCreated) {
                            toastMessage("Your task is added");
                        }
                    }
                });

            }
        });

        return rootView;
    }





    private void getUserData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            uInfo.setName(ds.child(userID).getValue(UserInformation.class).getName());
            uInfo.setGroup(ds.child(userID).getValue(UserInformation.class).getGroup());
            uInfo.setRole(ds.child(userID).getValue(UserInformation.class).getRole());
            uInfo.setSurname(ds.child(userID).getValue(UserInformation.class).getSurname());
        }


//        for(ds : dataSnapshot.getChildren()){
//            uInfo.setName(ds.child("Users").child(userID).getValue(UserInformation.class).getName());
//            uInfo.setName(ds.child("Users").child(userID).getValue(UserInformation.class).getSurname());
//            uInfo.setName(ds.child("Users").child(userID).getValue(UserInformation.class).getGroup());
//            uInfo.setName(ds.child("Users").child(userID).getValue(UserInformation.class).getRole());
//        }
    }

    private void toastMessage(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}