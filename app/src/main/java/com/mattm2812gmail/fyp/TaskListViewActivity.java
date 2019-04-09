package com.mattm2812gmail.fyp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskListViewActivity extends AppCompatActivity implements AddTask.OnInputListener{

    private static final String TAG = "TaskListViewActivity";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    public String userID;
    private String taskNameForFragment;

    public TextView listName;
    public ArrayList<Task> tasks;
    public TaskListAdapter taskListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_view_activity);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        userID = mFirebaseUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

        listName = findViewById(R.id.list_name);

        // set up array list to house tasks
        tasks = new ArrayList<>();

        // set up recycler view
        RecyclerView recyclerView = findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // set the task list adapter to the recycler view
        taskListAdapter = new TaskListAdapter(this, tasks);
        recyclerView.setAdapter(taskListAdapter);

        getIncomingIntent();
        taskListAdapter.notifyDataSetChanged();
    }

    // get task list name
    public String getMyData() {
        taskNameForFragment = listName.getText().toString();
        return taskNameForFragment;
    }

    public void addTask(View view){
        // open menu then add task
        AddTask addTaskBottomDialogFragment = AddTask.newInstance();
        addTaskBottomDialogFragment.show(getSupportFragmentManager(),
                "add_task_dialog_fragment");

    }

    // get data from recycler view adapater
    // using a bundle
    private void getIncomingIntent(){
        Bundle data = getIntent().getExtras();
        if (getIntent().hasExtra("taskList")) {
            String taskListName = data.getString("taskList");
            setTaskName(taskListName);
            getName(taskListName);
        }

        if (getIntent().hasExtra("tasks")){
            HashMap<String, HashMap<String, String>> newTasks = (HashMap<String, HashMap<String, String>>) data.getSerializable("tasks");
            String taskListName = data.getString("taskList");
            setData(newTasks, taskListName);
        }
    }

    // set the task name
    private void setTaskName(String taskListName){
        listName = findViewById(R.id.list_name);
        listName.setText(taskListName);
    }

    private void setData(HashMap<String, HashMap<String, String>> newTasks, String name){
        // iterate over the hashmap and set the task data
        for (Map.Entry<String, HashMap<String, String>> entry : newTasks.entrySet()) {
            String taskName = entry.getKey();
            HashMap<String, String> itMap = entry.getValue();

            String task = itMap.get("task");
            String subtask = itMap.get("subtask");
            String date = itMap.get("date");
            String parentName = itMap.get("parentName");

            Task newTask = new Task(task, subtask, date, parentName);
            tasks.add(newTask);
            taskListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void sendInput(Task input) {
        Task newTask = input;
        createNewTask(newTask);

    }

    public void createNewTask(Task newTask){
        String taskListName = listName.getText().toString();

        tasks.add(newTask);
        taskListAdapter.notifyDataSetChanged();

        HashMap<String, String> taskMap = new HashMap<>();
        taskMap.put("date", newTask.getDate());
        taskMap.put("subtask", newTask.getSubtask());
        taskMap.put("task", newTask.getTask());
        taskMap.put("parentName", newTask.getParentName());
        mDatabase.child("TaskList").child(taskListName).child("mHashMap").child(newTask.getTask()).setValue(newTask);
    }

    public String getName(String taskListName){
         taskListName = taskNameForFragment;
         return taskNameForFragment;
    }
}
