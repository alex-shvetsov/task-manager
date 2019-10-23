package com.example.lab3;

public class Task {
    private String name;
    private String comment;
    private String date;
    private boolean done;

    public Task(String name, String date, String comment, boolean done) {
        this.name = name;
        this.date = date;
        this.comment = comment;
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }
}
