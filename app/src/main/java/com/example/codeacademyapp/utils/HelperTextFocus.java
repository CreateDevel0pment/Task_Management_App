package com.example.codeacademyapp.utils;

import android.view.View;
import android.widget.EditText;

public class HelperTextFocus {

    public static void setFocus (final EditText editText){

        editText.setCursorVisible(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setCursorVisible(true);
            }
        });
    }
}
