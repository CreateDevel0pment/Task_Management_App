package com.example.codeacademyapp.main.group;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.sign_in.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment {

    public static final String TAG ="CHAT_TAG" ;
    private ImageButton sentMesage_btn;
    private EditText userMessage_input;
    private ScrollView scrollView;
    private TextView display_text_messages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_chat, container, false);

        if(getArguments()!=null){
            String nameOfTHeGroup=getArguments().getString("TITLE");
            setTitle(nameOfTHeGroup + " Group");
        }
        initialisedFields(view);

        return view;
    }
    private void initialisedFields(View view) {
        sentMesage_btn=view.findViewById(R.id.sent_message_btn);
        userMessage_input=view.findViewById(R.id.input_group_message);
        display_text_messages=view.findViewById(R.id.group_chat_display);
        scrollView=view.findViewById(R.id.scroll_view);
    }

}
