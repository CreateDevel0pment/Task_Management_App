package com.example.codeacademyapp.ui.main.sector.task.fragment;

import com.example.codeacademyapp.data.model.AssignedUsers;

import java.util.List;

public interface UsersToAssignDialogListener {

    void passListOfUsersToAddNewTaskFragment(List<AssignedUsers> list);

}
