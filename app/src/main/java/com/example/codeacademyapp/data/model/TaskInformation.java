package com.example.codeacademyapp.data.model;

public class TaskInformation {

    private String Note;
    private String Description;
    private String Group;
    private String Name;
    private String TimeCreated;
    private String TaskPriority;

    public TaskInformation() {
    }

    public TaskInformation(String name, String description, String note,  String group, String timeCreated, String taskPriority) {
        this.Name = name;
        this.Description = description;
        this.Note = note;
        this.Group = group;
        this.TimeCreated = timeCreated;
        this.TaskPriority = taskPriority;
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

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        this.Group = group;
    }

    public String getTimeCreated() {
        return TimeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        TimeCreated = timeCreated;
    }
}
