package com.example.codeacademyapp.ui.main.edit_find.find_friends;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.adapters.FirebaseRecycler;
import com.example.codeacademyapp.data.model.ModelFirebase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllContactsFragment extends Fragment {

    private FirebaseRecycler adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        DatabaseReference usersRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users");

        FirebaseRecyclerOptions<ModelFirebase> options =
                new FirebaseRecyclerOptions.Builder<ModelFirebase>()
                        .setQuery(usersRef, ModelFirebase.class)
                        .build();

        adapter = new FirebaseRecycler(options, getFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_all_friends_list, container, false);

        RecyclerView find_friends_recycler_list = view.findViewById(R.id.find_friends_recycler);
        find_friends_recycler_list.setLayoutManager(new LinearLayoutManager(getContext()));

        find_friends_recycler_list.setAdapter(adapter);
        adapter.startListening();

        return view;
    }
}
