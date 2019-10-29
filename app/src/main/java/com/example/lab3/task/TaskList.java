package com.example.lab3.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public Task[] get() {
        return taskList.toArray(new Task[0]);
    }

    public boolean add(Task task) {
        return taskList.add(task);
    }

    public boolean remove(Task task){
        return taskList.remove(task);
    }
}
