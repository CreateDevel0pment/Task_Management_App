package com.example.codeacademyapp.data.model;

import java.util.List;

public class Task {

    private String name;
    private String description;
    private String note;
    private int state;
    private String importance;
    private String start_date;
    private String endDate;
    private String user_Id;
    private String id;
    private String group;
    private String assignedUserId;
    private List<AssignedUsers> assignedUsers;


    public Task() {
    }

    public Task(String name, String description, String note) {
        this.name = name;
        this.description = description;
        this.note = note;
    }

    public Task(String name, String description, String note, int state, String importance, String start_date,
                String due_date, String user_Id, String id, String assignedUserId) {
        this.name = name;
        this.description = description;
        this.note = note;
        this.state = state;
        this.importance = importance;
        this.start_date = start_date;
        this.endDate = due_date;
        this.user_Id = user_Id;
        this.id = id;
        this.assignedUserId = assignedUserId;
    }

    public Task(String name, String description, String note, int state, String importance, String due_date, String user_Id) {
        this.name = name;
        this.description = description;
        this.note = note;
        this.state = state;
        this.importance = importance;
        this.endDate = due_date;
        this.user_Id = user_Id;
    }

    public List<AssignedUsers> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<AssignedUsers> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
}
