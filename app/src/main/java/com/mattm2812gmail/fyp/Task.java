package com.mattm2812gmail.fyp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Task implements Parcelable {

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

    public Task(Parcel in){
//        String[] data = new String[3];
//        in.readStringArray(data);

        this.task = in.readString();
        this.subtask = in.readString();
        this.date = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(task);
        dest.writeString(subtask);
        dest.writeString(date);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

}
