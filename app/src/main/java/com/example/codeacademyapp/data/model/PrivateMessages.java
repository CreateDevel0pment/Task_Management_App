package com.example.codeacademyapp.data.model;

public class PrivateMessages {

    private String from,message,type,time, name;

    public PrivateMessages(String from, String message, String type, String time, String docName) {
        this.from = from;
        this.message = message;
        this.type = type;
        this.time = time;
        this.name = docName;
    }

    public PrivateMessages() {
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
