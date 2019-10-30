package com.example.lab3.json;

import com.example.lab3.task.Task;

import java.util.ArrayList;

class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public Task[] get() {
        return taskList.size() > 0 ? taskList.toArray(new Task[0]) : new Task[0];
    }

    public boolean add(Task task) {
        return taskList.add(task);
    }

    public boolean remove(Task task){
        return taskList.remove(task);
    }
}
