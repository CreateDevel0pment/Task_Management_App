package com.example.codeacademyapp.data.model;

public class PrivateMessages {

    private String from,message,type;

    public PrivateMessages(String from, String message, String type) {
        this.from = from;
        this.message = message;
        this.type = type;
    }

    public PrivateMessages() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
