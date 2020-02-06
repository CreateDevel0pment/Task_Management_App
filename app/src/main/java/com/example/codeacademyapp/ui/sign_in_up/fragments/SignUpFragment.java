package com.example.codeacademyapp.ui.sign_in_up.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.codeacademyapp.data.model.User;
import com.example.codeacademyapp.ui.main.MainActivity;
import com.example.codeacademyapp.ui.main.sector.chat.ChatViewModel;
import com.example.codeacademyapp.ui.sign_in_up.UserViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.codeacademyapp.utils.Constants.USER;
import static com.example.codeacademyapp.utils.HelperTextFocus.setFocus;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private EditText name_et;
    private EditText mail_et, password_et;
    private Spinner position_spiner, sector_spiner;
    private CircleImageView userImageView;

    public static final int GALLERY_PICK = 1;

    private String position_string;
    private String sector_string;

    private UserViewModel userViewModel;
    private ChatViewModel groupChatViewModel;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initializedView(view);

        userViewModel = ViewModelProviders.of(SignUpFragment.this).get(UserViewModel.class);
        groupChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Set Your Profile Image?");
                alertDialogBuilder.setMessage("Set your image in edit profile, after you Sign Up");
                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });

        position_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position_spiner.getSelectedItem().equals("Manager")) {
                    position_string = "Manager";
                } else if (position_spiner.getSelectedItem().equals("Staff")) {
                    position_string = "Staff";
                } else {
                    position_string = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sector_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sector_spiner.getSelectedItem().equals("Android")) {
                    sector_string = "Android";
                } else if (sector_spiner.getSelectedItem().equals("Web Development")) {
                    sector_string = "Web Development";
                } else if (sector_spiner.getSelectedItem().equals("Ruby on rails")) {
                    sector_string = "Ruby on rails";
                } else {
                    sector_string = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button sign_up_btn = view.findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mail_et.getText().toString().isEmpty()) {
                    mail_et.setError("Enter email");
                } else if (password_et.getText().toString().isEmpty()) {
                    password_et.setError("Enter password");
                } else if (name_et.getText().toString().isEmpty()) {
                    name_et.setError("Enter name");
                } else if (position_string.isEmpty()) {
                    Toast.makeText(getContext(), "Choose position!", Toast.LENGTH_SHORT).show();
                } else if (sector_string.isEmpty()) {
                    Toast.makeText(getContext(), "Choose group!", Toast.LENGTH_SHORT).show();
                } else {

                    User user = new User();
                    user.seteMail(mail_et.getText().toString());
                    user.setPassword(password_et.getText().toString());
                    user.setName(name_et.getText().toString());
                    user.setSector_spinner(sector_string);
                    user.setPosition_spinner(position_string);

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

                    groupChatViewModel.setGroupNameToFirebase(sector_string);
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("TITLE", sector_string);
                    startActivity(intent);
                }
            }
        });

        return view;
    }


    private void toastMessage() {
        Toast.makeText(getContext(), "User exist", Toast.LENGTH_SHORT).show();
    }

    private void goToMainActivity(User user) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(USER, user.getName());
        startActivity(intent);
    }

    private void initializedView(View view) {

        name_et = view.findViewById(R.id.person_name);
        setFocus(name_et);
        mail_et = view.findViewById(R.id.person_email);
        setFocus(mail_et);
        password_et = view.findViewById(R.id.person_password);
        setFocus(password_et);

        position_spiner = view.findViewById(R.id.edit_spinner_role);
        sector_spiner = view.findViewById(R.id.edit_spinner_group);

        userImageView = view.findViewById(R.id.edit_circular_image);
    }
}
