package com.mattm2812gmail.fyp;

import java.util.ArrayList;

public class TaskList {

    private String listName;
    private ArrayList<Task> tasks;

    public TaskList(String listName, ArrayList<Task> tasks) {
        this.listName = listName;
        this.tasks = tasks;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
