package com.example.lab3;

import java.io.Serializable;
import java.util.Date;

public class Task {
    private String name;
    private String comment;
    private Date date;
    private boolean done;

    public Task(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public Task date(Date date) {
        this.date = date;
        return this;
    }

    public Task comment(String comment) {
        this.comment = comment;
        return this;
    }

    public Task fulfil(boolean done) {
        this.done = done;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }
}
