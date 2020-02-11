package com.example.codeacademyapp.data.model;

public class MessagesFromWall {

    String date;
    String group;
    String message;
    String name;
    String time;

    public MessagesFromWall(String date, String group, String message, String name, String time) {
        this.date = date;
        this.group = group;
        this.message = message;
        this.name = name;
        this.time = time;
    }

    public MessagesFromWall() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
