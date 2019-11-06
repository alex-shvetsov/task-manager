package com.example.lab3.data;

import java.util.ArrayList;
import java.util.Iterator;

class TaskList {
    private ArrayList<Task> taskList = new ArrayList<>();

    Task[] get() {
        return taskList.size() > 0 ? taskList.toArray(new Task[0]) : new Task[0];
    }

    void add(Task task) {
        taskList.add(task);
    }

    void remove(Task task){
        taskList.remove(task);
    }

    void removeAllDone(){
        Iterator it = taskList.iterator();
        while (it.hasNext()){
            Task task = (Task)it.next();
            if(task.isDone()) it.remove();
        }
    }
}
