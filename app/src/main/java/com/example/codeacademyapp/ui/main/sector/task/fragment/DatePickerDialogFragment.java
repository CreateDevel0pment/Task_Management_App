package com.example.codeacademyapp.ui.main.sector.task.fragment;


import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.example.codeacademyapp.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerDialogFragment extends DialogFragment {


    public DatePickerDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_date_picker_dialog, container, false);

        if (getDialog() != null) {
            Objects.requireNonNull(getDialog()
                    .getWindow())
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        final DatePicker datePicker = rootView.findViewById(R.id.datePicker);
        Button datePickerBtn = rootView.findViewById(R.id.pick_a_date_btn);

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                String yearString = String.valueOf(year);

                String monthString;
                if(month>=9){
                    monthString = String.valueOf(month+1);}
                else {
                    monthString = "0"+ (month + 1);
                }

                String dayString = String.valueOf(day);

                String date = dayString + "/" + monthString + "/" + yearString;

                DatePickerDialogListener datePickerDialogListener = (DatePickerDialogListener) getTargetFragment();

                if (datePickerDialogListener != null) {
                    datePickerDialogListener.passDateString(date);
                }
                if (getDialog() != null) {
                    dismiss();
                }
            }
        });

        return rootView;
    }

}
