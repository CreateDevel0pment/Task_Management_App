package com.example.codeacademyapp;

public class TaskInformation {

    private String Note;
    private String Description;
    private String Group;
    private String Name;
    private String TimeCreated;

    public TaskInformation() {
    }

    public TaskInformation(String name, String description, String note,  String group, String timeCreated) {
        this.Name = name;
        this.Description = description;
        this.Note = note;
        this.Group = group;
        this.TimeCreated = timeCreated;
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
