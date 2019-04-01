package com.mattm2812gmail.fyp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TaskListViewActivity extends AppCompatActivity implements AddTask.OnInputListener{

    private static final String TAG = "TaskListViewActivity";

    public TextView listName;
    public ListView taskList;
    public ArrayList<Task> tasks;
    public TaskListAdapter taskListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_view_activity);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        String name = "New Task";
        String subtask = "subtask";
        String date = "30/03";
        taskList = findViewById(R.id.task_list);
        listName = findViewById(R.id.list_name);

        tasks = new ArrayList<>();
        taskListAdapter = new TaskListAdapter(this, R.layout.task_view_layout, tasks);
        taskList.setAdapter(taskListAdapter);

        getIncomingIntent();
        taskListAdapter.notifyDataSetChanged();
    }

    public void addTask(View view){
//        // open menu then add task
        AddTask addTaskBottomDialogFragment = AddTask.newInstance();
        addTaskBottomDialogFragment.show(getSupportFragmentManager(),
                "add_task_dialog_fragment");

    }

    private void getIncomingIntent(){
        Bundle data = getIntent().getExtras();
        if (getIntent().hasExtra("taskList")) {
            String taskListName = data.getString("taskList");

            setTaskName(taskListName);
        }

        if (getIntent().hasExtra("tasks")){
            int position = data.getInt("position");
            ArrayList<Task> newTasks = data.getParcelableArrayList("tasks");
            Task test =  newTasks.get(position);
            String newTest = test.getTask();
            setData(newTasks);
        }
    }

    private void setTaskName(String taskListName){
        listName = findViewById(R.id.list_name);
        listName.setText(taskListName);
    }

    private void setData(ArrayList<Task> newTasks){
        int i = 0;
        tasks = newTasks;
        taskListAdapter.notifyDataSetChanged();
        Log.d(TAG, "setData: " + tasks.get(i).getTask());

    }

    @Override
    public void sendInput(Task input) {
        Task newTask = input;
        createNewTask(newTask);

    }

    public void createNewTask(Task newTask){
        tasks.add(newTask);
        taskListAdapter.notifyDataSetChanged();
    }


}
