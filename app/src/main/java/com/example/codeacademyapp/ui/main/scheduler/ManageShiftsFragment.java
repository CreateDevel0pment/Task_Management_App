package com.example.codeacademyapp.ui.main.scheduler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.UserModelFirebase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageShiftsFragment extends Fragment {
    private List<UserModelFirebase> firstShift;
    private List<UserModelFirebase> secondShift;


    public ManageShiftsFragment(List<UserModelFirebase> firstShift, List<UserModelFirebase> secondShift) {
        this.firstShift = firstShift;
        this.secondShift = secondShift;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_manage_shifts, container, false);
        RecyclerView rvFirst = view.findViewById(R.id.rvRequestsFirst);
        RecyclerView rvSecond = view.findViewById(R.id.rvRequestsSecond);

        UserNamesAdapter userAdapterFirst = new UserNamesAdapter(firstShift);
        rvFirst.setAdapter(userAdapterFirst);
        RecyclerView.LayoutManager layoutManagerFirst = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvFirst.setLayoutManager(layoutManagerFirst);


        UserNamesAdapter userAdapterSecond = new UserNamesAdapter(secondShift);
        rvSecond.setAdapter(userAdapterSecond);
        RecyclerView.LayoutManager layoutManagerSecond = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvSecond.setLayoutManager(layoutManagerSecond);

        return view;
    }
}
