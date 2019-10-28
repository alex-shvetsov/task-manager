package com.example.lab3;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public Task[] get() {
        return taskList.toArray(new Task[0]);
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void removeTask(Task task){
        taskList.remove(task);
    }

    public void toJson() {

    }

    public void fromJson() {

    }
}
