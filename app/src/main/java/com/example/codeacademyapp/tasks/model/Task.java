package com.example.codeacademyapp.tasks.model;

public class Task {

    String name;
    String description;
    String note;
    int state;
    int importance;
    String start_date;
    String due_date;
    String user_Id;

    public Task() {
    }

    public Task(String name, String description, String note, int state, int importance, String start_date, String due_date, String user_Id) {
        this.name = name;
        this.description = description;
        this.note = note;
        this.state = state;
        this.importance = importance;
        this.start_date = start_date;
        this.due_date = due_date;
        this.user_Id = user_Id;
    }

    public Task(String name, String description, String note, int state, int importance, String due_date, String user_Id) {
        this.name = name;
        this.description = description;
        this.note = note;
        this.state = state;
        this.importance = importance;
        this.due_date = due_date;
        this.user_Id = user_Id;
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

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }
}
