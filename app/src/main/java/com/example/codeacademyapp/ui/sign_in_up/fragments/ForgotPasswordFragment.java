package com.example.codeacademyapp.ui.sign_in_up.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.sign_in_up.LogUserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {

    private LogUserViewModel userViewModel;
    private EditText mail_et;

    public ForgotPasswordFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        userViewModel = ViewModelProviders.of(ForgotPasswordFragment.this).get(LogUserViewModel.class);


        mail_et = rootView.findViewById(R.id.recovery_email_editText);
        Button resetPassBtn = rootView.findViewById(R.id.send_recovery_mail_ok_btn);
        resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail_et.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    toastMessage("Please enter your Email");
                } else {
                    userViewModel.forgotPassword(email);
                    toastMessage("Check your Email account: " + "\n" + email);
                }

                getActivity().onBackPressed();

            }
        });

        return rootView;
    }

    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
