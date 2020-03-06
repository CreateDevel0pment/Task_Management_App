package com.example.codeacademyapp.data.model;

public class PublicMessage {

    private String date;
    private String id;
    private String sector;
    private String message;
    private String name;
    private String time;
    private String image;
    private String docType;
    private String docName;

    public PublicMessage() {
    }

    public PublicMessage(String date, String id, String sector, String message, String name,
                         String time, String image, String docType, String docName) {
        this.date = date;
        this.id = id;
        this.sector = sector;
        this.message = message;
        this.name = name;
        this.time = time;
        this.image = image;
        this.docType = docType;
        this.docName = docName;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getSector() {
        return sector;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

    public String getDocType() {
        return docType;
    }

    public String getDocName() {
        return docName;
    }
}
