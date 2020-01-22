package com.example.codeacademyapp.ui.group;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codeacademyapp.R;
import com.example.codeacademyapp.tasks.model.Task;
import com.example.codeacademyapp.tasks.viewmodel.TaskViewModel;
import com.example.codeacademyapp.ui.SignUpFragment;
import com.example.codeacademyapp.users.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewTaskFragment extends Fragment {


    private EditText task_name, task_description, task_note;
    private Button create_task;
//    String taskName;
//    String taskDesc;
//    String taskNote;

    TaskViewModel taskViewModel;

    public AddNewTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_new_task, container, false);

        task_name = rootView.findViewById(R.id.task_name);
        task_description = rootView.findViewById(R.id.task_desc);
        task_note = rootView.findViewById(R.id.task_note);
        create_task = rootView.findViewById(R.id.create_task_btn);

        taskViewModel= ViewModelProviders.of(AddNewTaskFragment.this).get(TaskViewModel.class);



        create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task task = new Task();
                task.setName(task_name.getText().toString());
                task.setDescription(task_description.getText().toString());
                task.setNote( task_note.getText().toString());

                taskViewModel.createNewTask(task);
                taskViewModel.getCreateNewTaskLiveData().observe(AddNewTaskFragment.this, new Observer<Task>() {
                    @Override
                    public void onChanged(Task task) {
                        if(task.isCreated){
                            toastMessage("Your task is added");
                        }
                    }
                });

            }
        });



        return rootView;
    }
    private void toastMessage(String message){

        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
}
