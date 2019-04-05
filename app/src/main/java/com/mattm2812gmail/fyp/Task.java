package com.mattm2812gmail.fyp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;

public class Task {

    private String taskName, subtask;
//    private HashMap<String, HashMap<String, String>> tasks;
    private String date;

    public Task(String task, String subtask, String date) {
        this.taskName = task;
        this.subtask = subtask;
        this.date = date;
//        this.tasks = tasks;
    }

    public String getTask() {
        return taskName;
    }

//    public HashMap<String, HashMap<String, String>> getTasks() {return tasks;}

    public String getSubtask() {
        return subtask;
    }

    public String getDate() {
        return date;
    }

    public void setTask(String task) {
        this.taskName = task;
    }

//    public void setTasks(HashMap<String, HashMap<String, String>> tasks) {this.tasks = tasks;}

    public void setSubtask(String subtask) {
        this.subtask = subtask;
    }

    public void setDate(String date) {
        this.date = date;
    }

//    public Task(Parcel in){
////        String[] data = new String[3];
////        in.readStringArray(data);
//
//        this.taskName = in.readString();
//        this.subtask = in.readString();
//        this.date = in.readString();
//
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(taskName);
//        dest.writeString(subtask);
//        dest.writeString(date);
//    }
//
//    public static final Creator<Task> CREATOR = new Creator<Task>() {
//        @Override
//        public Task createFromParcel(Parcel in) {
//            return new Task(in);
//        }
//
//        @Override
//        public Task[] newArray(int size) {
//            return new Task[size];
//        }
//    };

}
