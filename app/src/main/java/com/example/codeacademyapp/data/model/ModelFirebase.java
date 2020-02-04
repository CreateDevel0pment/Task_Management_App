package com.example.codeacademyapp.data.model;

public class ModelFirebase {

    public String Name,Sector,image;

    public ModelFirebase() {
    }

    public ModelFirebase(String name, String group, String image) {
        Name = name;
        Sector = group;
        this.image = image;
    }
}
