package com.mattm2812gmail.fyp;

public class Task {

    private String taskName, subtask, parentName;
    private String date;

    public Task(String task, String subtask, String date, String parentName) {
        this.taskName = task;
        this.parentName = parentName;
        this.subtask = subtask;
        this.date = date;
    }

    public String getTask() {
        return taskName;
    }

    public String getSubtask() {
        return subtask;
    }

    public String getDate() {
        return date;
    }

    public void setTask(String task) {
        this.taskName = task;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setSubtask(String subtask) {
        this.subtask = subtask;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
