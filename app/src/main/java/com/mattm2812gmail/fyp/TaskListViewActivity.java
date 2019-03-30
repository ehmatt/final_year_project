package com.mattm2812gmail.fyp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TaskListViewActivity extends AppCompatActivity {

    public TextView listName;
    public ListView taskList;
    public ArrayList<Task> tasks;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_view_activity);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getIncomingIntent();

        String name = "New Task";
        String subtask = "subtask";
        String date = "30/03";
        tasks = new ArrayList<>();
        Task mTask = new Task(name, subtask, date);
        tasks.add(mTask);

        taskList = findViewById(R.id.)

//        Bundle data = getIntent().getExtras();
//        String taskListName = data.getString("name");
        listName = findViewById(R.id.list_name);
//        listName.setText(taskListName);

    }

    public void addTask(View view){
//        // open menu then add task
        AddTask addTaskBottomDialogFragment = AddTask.newInstance();
        addTaskBottomDialogFragment.show(getSupportFragmentManager(),
                "add_task_dialog_fragment");

    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("taskList")){
            String taskListName = getIntent().getStringExtra("taskList");
            setData(taskListName);
        }
    }

    private void setData(String taskListName){
        listName = findViewById(R.id.list_name);
        listName.setText(taskListName);
    }


//    @Override
//    public void sendInput(String input) {
//        String mInput = input;
//        setTextView(mInput);
//    }
//
//    public void setTextView(String mInput){
//        listName.setText(mInput);
//    }
}
