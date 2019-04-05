package com.mattm2812gmail.fyp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskList {

    private String taskListName;
    private HashMap<String, HashMap<String, String>> mHashMap;

    public TaskList(){}

    public TaskList(String listName, HashMap<String, HashMap<String, String>> hashMap){
        taskListName = listName;
        mHashMap = hashMap;
    }

    public String getTaskListName(){
        return taskListName;
    }

    public HashMap<String, HashMap<String, String>> getmHashMap(){
        return mHashMap;
    }

    public void setTaskListName(String taskListName) {
        this.taskListName = taskListName;
    }

    public void setmHashMap(HashMap<String, HashMap<String, String>> mHashMap) {
        this.mHashMap = mHashMap;
    }

    //    private String listName;
//    private HashMap<String, ArrayList> tasks;
//
//    public TaskList(String listName, HashMap<String, ArrayList> tasks) {
//        this.listName = listName;
//        this.tasks = tasks;
//    }
//
//    public TaskList(){
////        listName = ds.child("listName").getValue(String.class);
////        GenericTypeIndicator<ArrayList<Task>> tasks = new GenericTypeIndicator<ArrayList<Task>>() {};
//    }
//
//
//
//    public String getListName() {
//        return listName;
//    }
//
//    public void setListName(String listName) {
//        this.listName = listName;
//    }
//
//    public HashMap<String, ArrayList> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(HashMap<String, ArrayList> tasks) {
//        this.tasks = tasks;
//    }
}
