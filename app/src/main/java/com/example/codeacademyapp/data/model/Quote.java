package com.example.codeacademyapp.data.model;

import androidx.annotation.NonNull;

public class Quote {


    @NonNull
    private String id;
    private String author;
    private String en;
    private double rating;
    private int numberOfVotes;

    public Quote() {}


    public Quote(String author, String en, double rating, String id, int numberOfVotes) {
        this.author = author;
        this.en = en;
        this.rating = rating;
        this.id = id;
        this.numberOfVotes = numberOfVotes;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
