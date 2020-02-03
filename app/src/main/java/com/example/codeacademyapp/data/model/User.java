package com.example.codeacademyapp.data.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class User implements Serializable {

    public String id_user;
    public String eMail;
    public String password;
    public String name;
    public String surname;
    public String role_spinner;
    public String group_spinner;


    @Exclude
    public boolean isAuthenticated;

    @Exclude
    public boolean isNew,isCreated;

    public User() {
    }

    public User(String eMail, String password, String user_id ) {
        this.eMail = eMail;
        this.password = password;
        this.id_user=user_id;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole_spinner() {
        return role_spinner;
    }

    public void setRole_spinner(String role_spinner) {
        this.role_spinner = role_spinner;
    }

    public String getGroup_spinner() {
        return group_spinner;
    }

    public void setGroup_spinner(String group_spinner) {
        this.group_spinner = group_spinner;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
