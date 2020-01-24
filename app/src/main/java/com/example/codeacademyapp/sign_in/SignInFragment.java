package com.example.codeacademyapp.sign_in;


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
import com.example.codeacademyapp.StartActivity;
import com.example.codeacademyapp.model.User;

import static com.example.codeacademyapp.utils.HelperTextFocus.setFocus;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private EditText mail_et, password_et;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        //TODO splash
        userViewModel = ViewModelProviders.of(SignInFragment.this).get(UserViewModel.class);

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

                User user = new User();
                user.seteMail(mail);
                user.setPassword(password);

                userViewModel.signInNewUser(mail, password);
                userViewModel.getAuthenticatedUserLiveData().observe(SignInFragment.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {

                        if (user.isNew) {
//                            createNewUser(user);
                            toastMessage("Please SignUp!");
                        } else {
                            goToMainActivity(user);
                        }
                    }
                });
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail_et.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    toastMessage("Please enter your Email");
                } else {
                    userViewModel.forgotPassword(email);
                    toastMessage("Check your Email account: " + "\n" + email);
                }
            }
        });

        return view;
    }

    private void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void goToMainActivity(User user) {
        Intent intent = new Intent(getContext(), StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra(USER, user.getName());
        startActivity(intent);
//        finish();
    }
}