package com.example.codeacademyapp.ui.main.home;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.sign_in_up.fragments.BaseFragment;
import com.google.firebase.database.DataSnapshot;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private LinearLayout web_layout;
    private EditText web_text;
    private Button ok_btn;
    private View view;
    private HomeViewModel homeViewModel;
    private String getUrl;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        homeViewModel.getStringUrl().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {

                web_layout = view.findViewById(R.id.home_welcome_layout);

                if (dataSnapshot.exists()) {
                    getUrl = dataSnapshot.getValue().toString();

                    WebView browser = view.findViewById(R.id.home_web_view);
                    browser.setBackgroundColor(Color.TRANSPARENT);
                    CardView cardView = view.findViewById(R.id.web_card_view);

                    browser.loadUrl(getUrl);
                    browser.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                } else {
                    web_layout.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {

            return view;
        }

        view = inflater.inflate(R.layout.fragment_home, container, false);
        setTitle(R.string.home);

        web_text = view.findViewById(R.id.home_enter_website_text);
        ok_btn = view.findViewById(R.id.home_ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (web_text.getText().toString().equals("")) {

                    web_text.setError("Enter web");

                } else {

                    String webUrl = web_text.getText().toString();
                    homeViewModel.setHomePageUrl(webUrl);

                    HomeFragment homeFragment = new HomeFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.home_container, homeFragment)
                            .commit();

                }
            }
        });

        return view;
    }

}
