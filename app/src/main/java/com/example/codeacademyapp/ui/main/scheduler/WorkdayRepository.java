package com.example.codeacademyapp.ui.main.scheduler;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.codeacademyapp.data.model.User;
import com.example.codeacademyapp.data.model.UserModelFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class WorkdayRepository {

    private MutableLiveData<Workday> setWorkdayInfo;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef, myShiftRef;
    private FirebaseAuth mAuth;
    private String userID, userName, userImg;

    public WorkdayRepository(){
        setWorkdayInfo = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<Workday> addWorkdaysForUser(final Workday workday){

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        myShiftRef = firebaseDatabase.getReference();

        FirebaseUser userFb = mAuth.getCurrentUser();

        if (userFb != null) {
            userID = userFb.getUid();
        }

       myRef.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    userName = (Objects.requireNonNull(dataSnapshot.getValue(UserModelFirebase.class))).Name;
                    userImg = (Objects.requireNonNull(dataSnapshot.getValue(UserModelFirebase.class))).image;

                    firebaseDatabase.getReference().child("Schedule").child(workday.getDate().toString().substring(0,10)).child(workday.getShift())
                            .child(userID)
                            .child("id")
                            .setValue(userID);
                    firebaseDatabase.getReference().child("Schedule").child(workday.getDate().toString().substring(0,10)).child(workday.getShift())
                            .child(userID)
                            .child("Name")
                            .setValue(userName);
                    firebaseDatabase.getReference().child("Schedule").child(workday.getDate().toString().substring(0,10)).child(workday.getShift())
                            .child(userID)
                            .child("image")
                            .setValue(userImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(workday.getShift()!=null){
            myRef.child("Users").child(userID).child("Schedule").child(workday.getDate().toString().substring(0,10))
                    .child("Shift")
                    .setValue(workday.getShift());

        }



        return setWorkdayInfo;
    }

    public MutableLiveData<DataSnapshot> getWorkDayForUser(final Workday workday){
        final MutableLiveData<DataSnapshot> getWorkDayInfo = new MutableLiveData<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseUser userFb = mAuth.getCurrentUser();

        if (userFb != null) {
            userID = userFb.getUid();
        }

        myRef.child(userID).child("Schedule").child(workday.getDate().toString().substring(0,10))
                .child("Shift").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    getWorkDayInfo.setValue(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return getWorkDayInfo;
    }

    public MutableLiveData<DataSnapshot> getWorkdaySchedule(Workday workday){
        final MutableLiveData<DataSnapshot> getWorkDayShiftsSchedule = new MutableLiveData<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        myRef = FirebaseDatabase.getInstance().getReference().child("Schedule");


        myRef.child(workday.getDate().toString().substring(0,10))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            getWorkDayShiftsSchedule.setValue(dataSnapshot);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



        return getWorkDayShiftsSchedule;
    }
}
