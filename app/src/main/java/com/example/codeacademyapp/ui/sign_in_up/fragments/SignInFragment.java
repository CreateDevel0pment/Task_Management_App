package com.example.codeacademyapp.ui.sign_in_up.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.data.model.User;
import com.example.codeacademyapp.ui.main.MainActivity;
import com.example.codeacademyapp.ui.main.MainBoardActivity;
import com.example.codeacademyapp.ui.sign_in_up.LogUserViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

import static com.example.codeacademyapp.utils.HelperTextFocus.setFocus;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private EditText mail_et, password_et;
    private LogUserViewModel userViewModel;
    private UserInformationViewModel userInformationViewModel;
    private View view;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            return view;
        }

        view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        userViewModel = ViewModelProviders.of(SignInFragment.this).get(LogUserViewModel.class);
        userInformationViewModel = ViewModelProviders.of(SignInFragment.this).get(UserInformationViewModel.class);

        mail_et = view.findViewById(R.id.mail_text);
        setFocus(mail_et);
        password_et = view.findViewById(R.id.password_text);
        setFocus(password_et);
        Button log_in_btn = view.findViewById(R.id.log_in_btn);
        TextView forgot_pass = view.findViewById(R.id.forgot_pass);

        log_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = mail_et.getText().toString();
                String password = password_et.getText().toString();

                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Empty field error!", Toast.LENGTH_SHORT).show();
                } else {

                    User user = new User();
                    user.seteMail(mail);
                    user.setPassword(password);

                    userViewModel.signInNewUser(mail, password).observe(SignInFragment.this, new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if (firebaseUser != null) {
                                getReferencesForUserGroup();
                            } else {
                                Toast.makeText(getContext(), "Please Sign Up", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }

        });


        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ForgotPasswordFragment passwordFragment = new ForgotPasswordFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.start_activity_main_container, passwordFragment)
                        .addToBackStack("null")
                        .commit();
            }
        });

        return view;
    }

    public void getReferencesForUserGroup() {

        userInformationViewModel = ViewModelProviders.of(this).get(UserInformationViewModel.class);
        userInformationViewModel.getUserInformation().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.exists()) {

                        userId = dataSnapshot.getRef().getKey();
                        String userGroup = Objects.requireNonNull(dataSnapshot.child("Sector").getValue()).toString();
                        goToStartActivity(userGroup);
                    }
                }
            }
        });
    }

    private void goToStartActivity(String userGroup) {
        Intent intent = new Intent(getContext(), MainBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("TITLE", userGroup);
        startActivity(intent);
    }
}
