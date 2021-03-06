package com.example.codeacademyapp.data.model;

import android.net.Uri;

import java.util.List;

public class TaskInformation {


    private String Description;
    private String Sector;
    private String Name;
    private String TimeCreated;
    private String TaskPriority;
    private String EndDate;
    private String State;
    private String TaskRef;
    private String AssignedUser;
    private List<AssignedUsers> AssignedUsers;
    private List<CompletedBy> CompletedBy;
    private String DocType;
    private String DocName;
    private String DocPath;


    public TaskInformation() {
    }

    public TaskInformation(String name, String description,
                           String group, String timeCreated,
                           String taskPriority, String endDate, String taskRef, String docType,
                           String docName, String docPath) {
        this.Name = name;
        this.Description = description;
        this.Sector = group;
        this.TimeCreated = timeCreated;
        this.TaskPriority = taskPriority;
        this.EndDate = endDate;
        this.TaskRef = taskRef;
        this.DocName = docName;
        this.DocPath = docPath;
        this.DocType = docType;
    }

    public String getDocType() {
        return DocType;
    }

    public void setDocType(String docType) {
        this.DocType = docType;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        this.DocName = docName;
    }

    public String getDocPath() {
        return DocPath;
    }

    public void setDocPath(String docPath) {
        this.DocPath = docPath;
    }

    public String getTaskPriority() {
        return TaskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        TaskPriority = taskPriority;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        this.Sector = sector;
    }

    public String getTimeCreated() {
        return TimeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        TimeCreated = timeCreated;
    }

    public List<AssignedUsers> getAssignedUsers() {
        return AssignedUsers;
    }

    public void setAssignedUsers(List<AssignedUsers> assignedUsers) {
        this.AssignedUsers = assignedUsers;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getState() {
        return State;
    }
    public void setState(String state) {
        State = state;
    }

    public List<com.example.codeacademyapp.data.model.CompletedBy> getCompletedBy() {
        return CompletedBy;
    }

    public void setCompletedBy(List<com.example.codeacademyapp.data.model.CompletedBy> completedBy) {
        CompletedBy = completedBy;
    }

    public String getTaskRef() {
        return TaskRef;
    }

    public void setTaskRef(String taskRef) {
        TaskRef = taskRef;
    }
}
