package com.example.codeacademyapp.data.model;

import android.net.Uri;

import java.util.List;

public class Task {

    private String name;
    private String description;
    private String docType;
    private String docName;
    private int state;
    private String importance;
    private String start_date;
    private String endDate;
    private Uri uri;
    private String id;
    private String group;
    private String assignedUserId;
    private List<AssignedUsers> assignedUsers;


    public Task() {
    }

    public Task(String name, String description, String note) {
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, String docType, String docName,
                int state, String importance, String start_date,
                String due_date, String user_Id, String id, String assignedUserId) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.importance = importance;
        this.start_date = start_date;
        this.endDate = due_date;
        this.id = id;
        this.assignedUserId = assignedUserId;
        this.docName = docName;
        this.docType = docType;
    }

    public Task(String name, String description, int state, String importance, String due_date, String user_Id) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.importance = importance;
        this.endDate = due_date;

    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getDocType() {
        return docType;
    }

    public String getDocName() {
        return docName;
    }

    public Uri getUri() {
        return uri;
    }

    public List<AssignedUsers> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<AssignedUsers> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
}
