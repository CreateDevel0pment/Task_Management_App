package com.example.codeacademyapp.data.model;

public class UserModelFirebase {

    public String Name,Sector,image;

    public UserModelFirebase() {
    }

    public UserModelFirebase(String name, String group, String image) {
        Name = name;
        Sector = group;
        this.image = image;
    }
}
