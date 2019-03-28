package com.mattm2812gmail.fyp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AddTaskList.OnInputListener {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public ArrayList<String> items;
    public ArrayList<Task> tasks;
    public ArrayList<TaskList> taskList;

    public TaskListAdapter taskListAdapter;
    public RecyclerTypeAdapter recyclerTypeAdapter;

    public ArrayAdapter<String> itemsAdapter;

    public ListView lvItems;
    public Button btnAddTask, btnAddTaskList;
    public String mInput;
    public TextView mInputDisplay;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        btnAddTask = findViewById(R.id.button);
        btnAddTaskList = findViewById(R.id.task_list_btn);
        btnAddTask.setTextColor(Color.WHITE);
        btnAddTaskList.setTextColor(Color.WHITE);

        mInputDisplay = findViewById(R.id.testView);

        // firebase login check
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        taskList = new ArrayList<TaskList>();

        RecyclerView recyclerView = findViewById(R.id.task_type_list);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerTypeAdapter = new RecyclerTypeAdapter(this, taskList);
//        recyclerTypeAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerTypeAdapter);

        String listName = "College";
        taskList.add(new TaskList(listName));
        recyclerTypeAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void onItemClick(View view, int position) {
//        Log.i("TAG", "You clicked number " + recyclerTypeAdapter.getItem(position) + ", which is at cell position " + position);
//    }

    public void addTask(View view){
//        // open menu then add task
        AddTask addTaskBottomDialogFragment = AddTask.newInstance();
        addTaskBottomDialogFragment.show(getSupportFragmentManager(),
                "add_task_dialog_fragment");

    }

    public void addTaskList(View view){
        AddTaskList addTaskList = AddTaskList.newInstance();
        addTaskList.show(getSupportFragmentManager(),
                "add_task_list_dialog_fragment");
    }

    // MENU STUFF
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_action:
                // User chose the "Settings" item, show the app settings UI...
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        tasks.remove(pos);
                        // Refresh the adapter
//                        taskListAdapter.notifyDataSetChanged();
                        writeItems();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            tasks = new ArrayList<Task>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            tasks = new ArrayList<>();
        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendInput(String input) {
//        TaskList test = new TaskList(input);
//        taskList.add(test);

        mInput = input;
        setInputToTextView();
    }

    private void setInputToTextView(){
//        TaskList test = new TaskList(mInput);
//        taskList.add(test);
//        EditText etNewItem = findViewById(R.id.task_name);
////        String itemText = etNewItem.getText().toString();
//        itemsAdapter.add(mInput);
//        etNewItem.setText("");
    }
}
