package com.example.codeacademyapp.data.model;

import java.util.List;

public class TaskInformation {

    private String Note;
    private String Description;
    private String Sector;
    private String Name;
    private String TimeCreated;
    private String TaskPriority;
    private String EndDate;
    private List<AssignedUsers> AssignedUsers;


    public TaskInformation() {
    }

    public TaskInformation(String name, String description, String note,
                           String group, String timeCreated,
                           String taskPriority, String endDate) {
        this.Name = name;
        this.Description = description;
        this.Note = note;
        this.Sector = group;
        this.TimeCreated = timeCreated;
        this.TaskPriority = taskPriority;
        this.EndDate = endDate;
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

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        this.Note = note;
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
}
