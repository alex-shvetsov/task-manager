package com.example.lab3.data;

import java.util.Date;

public class Task {

    private String name;
    private String comment;
    private Date date;
    private boolean done;

    public Task(String name, Date date, String comment, boolean done) {
        this.name = name;
        this.date = date;
        this.comment = comment;
        this.done = done;
    }

    public Task() { }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String comment) {
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
