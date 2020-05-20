package com.example.codeacademyapp.ui.main.sector.task.fragment.listeners;

import com.example.codeacademyapp.data.model.AssignedUsers;
import com.example.codeacademyapp.data.model.User;
import com.example.codeacademyapp.data.model.UserModelFirebase;

import java.util.List;

public interface UsersToAssignDialogListener {

    void passListOfUsersToAddNewTaskFragment(String userId);

    void passListOfUsersFragment(List<User> ids);

}
