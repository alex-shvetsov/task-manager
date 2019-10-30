package com.example.lab3.json;

import com.example.lab3.task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

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

    public boolean removeIfDone(){
        Iterator it = taskList.iterator();
        while (it.hasNext()){
            Task task = (Task)it.next();
            if(task.isDone()) it.remove();
        }
        return true;
    }

    public boolean sortByDate(){
        Collections.sort(taskList, (task, t1)
                -> Long.compare(task.getDate().getTime(), t1.getDate().getTime()));
        return true;
    }
}
