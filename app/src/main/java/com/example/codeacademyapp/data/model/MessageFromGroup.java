package com.example.codeacademyapp.data.model;

public class MessageFromGroup {

    private String date;
    private String id;
    private String sector;
    private String message;
    private String name;
    private String time;
    private String image;

    public MessageFromGroup() {
    }

    public MessageFromGroup(String date, String id, String sector,
                            String message, String name, String time, String image) {
        this.date = date;
        this.id = id;
        this.sector = sector;
        this.message = message;
        this.name = name;
        this.time = time;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
