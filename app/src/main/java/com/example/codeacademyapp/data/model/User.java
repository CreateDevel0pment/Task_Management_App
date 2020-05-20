package com.example.codeacademyapp.data.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class User implements Serializable {

    public String id_user;
    public String eMail;
    public String password;
    public String name;
    public String position_spinner;
    public String sector_spinner;
    public String device_token;
    public String imageUrl;


    @Exclude
    public boolean isAuthenticated;

    @Exclude
    public boolean isNew,isCreated;

    public User() {
    }

    public User(String eMail, String password, String user_id,String device_token) {
        this.eMail = eMail;
        this.password = password;
        this.id_user=user_id;
        this.device_token=device_token;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition_spinner() {
        return position_spinner;
    }

    public void setPosition_spinner(String position_spinner) {
        this.position_spinner = position_spinner;
    }

    public String getSector_spinner() {
        return sector_spinner;
    }

    public void setSector_spinner(String sector_spinner) {
        this.sector_spinner = sector_spinner;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
