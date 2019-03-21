package com.mattm2812gmail.fyp;

import java.util.Date;

public class Task {

    private String task, subtask;
    private String date;

    public Task(String task, String subtask, String date) {
        this.task = task;
        this.subtask = subtask;
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public String getSubtask() {
        return subtask;
    }

    public String getDate() {
        return date;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setSubtask(String subtask) {
        this.subtask = subtask;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
