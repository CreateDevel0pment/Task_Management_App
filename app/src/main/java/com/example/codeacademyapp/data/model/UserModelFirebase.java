package com.example.codeacademyapp.data.model;

public class UserModelFirebase {

    public String Name,Sector,image, id;

    public UserModelFirebase() {
    }

    public UserModelFirebase(String name, String group, String image) {
        Name = name;
        Sector = group;
        this.image = image;
    }

    public UserModelFirebase(String name, String image) {
        this.Name = name;
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }
}
