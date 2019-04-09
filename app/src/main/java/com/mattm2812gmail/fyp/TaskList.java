package com.mattm2812gmail.fyp;

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
}
