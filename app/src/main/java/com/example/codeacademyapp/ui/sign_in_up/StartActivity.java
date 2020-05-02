package com.example.codeacademyapp.ui.sign_in_up;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.ui.sign_in_up.fragments.FragmentStartMain;
import com.example.codeacademyapp.utils.NetworkConnectivity;

public class StartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FragmentStartMain fragmentStartMain = new FragmentStartMain();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.start_activity_main_container, fragmentStartMain)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (NetworkConnectivity.isConnectivityNetworkAvailable(this)) {

            Toast connectivityToast = Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG);
            connectivityToast.setGravity(Gravity.CENTER, 0, 0);
            connectivityToast.show();
        }
    }
}