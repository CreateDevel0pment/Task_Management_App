package com.example.codeacademyapp.sign_in;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.main.GroupChatViewModel;
import com.example.codeacademyapp.main.StartActivity;
import com.example.codeacademyapp.model.User;

import static com.example.codeacademyapp.utils.Constants.USER;
import static com.example.codeacademyapp.utils.HelperTextFocus.setFocus;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private EditText name_et, surename_et;
    private EditText mail_et, password_et;
    private Spinner position_spiner, group_spiner;
    private Button sign_up_btn;

    private String role_string;
    private String group_string;

    private UserViewModel userViewModel;
    private GroupChatViewModel groupChatViewModel;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        userViewModel = ViewModelProviders.of(SignUpFragment.this).get(UserViewModel.class);
        groupChatViewModel = ViewModelProviders.of(this).get(GroupChatViewModel.class);

        name_et = view.findViewById(R.id.person_name);
        name_et.addTextChangedListener(loginTextWatcher);
        setFocus(name_et);
        surename_et = view.findViewById(R.id.person_sure_name);
        surename_et.addTextChangedListener(loginTextWatcher);
        setFocus(surename_et);
        sign_up_btn = view.findViewById(R.id.sign_up_btn);
        mail_et = view.findViewById(R.id.person_email);
        mail_et.addTextChangedListener(loginTextWatcher);
        setFocus(mail_et);
        password_et = view.findViewById(R.id.person_password);
        password_et.addTextChangedListener(loginTextWatcher);
        setFocus(password_et);

        position_spiner = view.findViewById(R.id.spinner_role);
        group_spiner = view.findViewById(R.id.spinner_group);


        position_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position_spiner.getSelectedItem().equals("HR")) {
                    role_string = "HR";

                } else if (position_spiner.getSelectedItem().equals("Tutor")) {
                    role_string = "Tutor";

                } else if (position_spiner.getSelectedItem().equals("Student")) {
                    role_string = "Student";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        group_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (group_spiner.getSelectedItem().equals("Android")) {
                    group_string = "Android";

                } else if (group_spiner.getSelectedItem().equals("Web Development")) {
                    group_string = "Web Development";

                } else if (group_spiner.getSelectedItem().equals("Ruby on rails")) {
                    group_string = "Ruby on rails";

                } else {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.seteMail(mail_et.getText().toString());
                user.setPassword(password_et.getText().toString());
                user.setName(name_et.getText().toString());
                user.setSurname(surename_et.getText().toString());
                user.setGroup_spinner(group_string);
                user.setRole_spinner(role_string);

                userViewModel.signUpNewUser(user).observe(SignUpFragment.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {

                        if (user.isNew) {
                            goToMainActivity(user);
                        } else {
                            toastMessage();
                        }
                    }
                });

                groupChatViewModel.setGroupNameOnFirebase(group_string);
                Intent intent = new Intent(getContext(), StartActivity.class);
                intent.putExtra("TITLE", group_string);
                startActivity(intent);
            }
        });

        return view;
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String mailInput = mail_et.getText().toString().trim();
            String nameInput = name_et.getText().toString().trim();
            String passInput = password_et.getText().toString().trim();
            String surNameInput = surename_et.getText().toString().trim();

            sign_up_btn.setEnabled(!mailInput.isEmpty() && !nameInput.isEmpty()
                    && !passInput.isEmpty() && !surNameInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void toastMessage() {
        Toast.makeText(getContext(), "User exist", Toast.LENGTH_SHORT).show();
    }

    private void goToMainActivity(User user) {
        Intent intent = new Intent(getContext(), StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(USER, user.getName());
        startActivity(intent);
//        finish();
    }
}
