package com.mattm2812gmail.fyp;

public class TaskView {

    public String task;
    public String subtask;
    public String date;

    public TaskView(String task, String subtask, String date) {
        this.task = task;
        this.subtask = subtask;
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getSubtask() {
        return subtask;
    }

    public void setSubtask(String subtask) {
        this.subtask = subtask;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
