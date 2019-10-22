package com.example.lab3;

import java.util.ArrayList;
import java.util.Date;

public class Json {
    public static ArrayList<Task> parse(String file) {
        ArrayList<Task> saved = new ArrayList<>();

        saved.add(new Task("Task 1", new Date(2000, 10, 1)));
        saved.add(new Task("Task 2", new Date(2000, 10, 1)));
        saved.add(new Task("Task 3", new Date(2000, 10, 1)));

        return saved;
    }
}
