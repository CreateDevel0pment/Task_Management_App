package com.example.codeacademyapp.data.model;

public class CompletedBy {

    private String userId;

    public CompletedBy() {
    }

    public CompletedBy(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
