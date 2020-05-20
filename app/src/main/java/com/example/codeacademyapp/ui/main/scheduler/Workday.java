package com.example.codeacademyapp.ui.main.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Workday {

  private String shift;
  private Date date;
  private String day;
  private String dayOfWeek;
  private String month;

    public Workday() {
    }

    public Workday(String shift, Date date, String dateString, String dayOfWeek, String month) {
        this.shift = shift;
        this.date = date;
        this.day = dateString;
        this.dayOfWeek = dayOfWeek;
        this.month = month;
    }

    public Workday( String shift, String day, Date date) {
        this.date = date;
        this.day = day;
        this.shift =shift;

    }

    public Workday(String day, Date date) {
        this.day = day;
        this.date = date;
    }

    public Workday(Date date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public Date getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getMonth() {
        return month;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDateString(String dateString) {
        this.day = dateString;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
