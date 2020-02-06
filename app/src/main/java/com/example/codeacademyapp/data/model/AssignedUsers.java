package com.example.codeacademyapp.data.model;

public class AssignedUsers {

    private String userId;

    public AssignedUsers() {
    }

    public AssignedUsers(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
