package com.mattm2812gmail.fyp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements AddTaskList.OnInputListener {
    private static final String TAG = "HomeActivity";
    
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;

    public ArrayList<Task> tasks;
    public ArrayList<TaskList> taskList;
    public HashMap<String, ArrayList<Task>> mHashMap;

    public TaskListAdapter taskListAdapter;
    public RecyclerTypeAdapter recyclerTypeAdapter;

    public ListView lvItems;
    public Button btnAddTask, btnAddTaskList;
    public String mInput;
    public TextView mInputDisplay;

    public String userID;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // firebase login check
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        userID = mFirebaseUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

//        String mUser = mDatabase.getRoot().child("users").child();

        btnAddTask = findViewById(R.id.button);
        btnAddTaskList = findViewById(R.id.task_list_btn);
        btnAddTask.setTextColor(Color.WHITE);
        btnAddTaskList.setTextColor(Color.WHITE);

        mInputDisplay = findViewById(R.id.testView);

        mHashMap = new HashMap<>();

        taskList = new ArrayList<TaskList>();
        RecyclerView recyclerView = findViewById(R.id.task_type_list);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerTypeAdapter = new RecyclerTypeAdapter(this, taskList);
        recyclerView.setAdapter(recyclerTypeAdapter);


        // DRAG N DROP
        ItemTouchHelper.Callback itemTouchCallback = new ItemTouchHelper.Callback() {
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(taskList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                recyclerTypeAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //TODO
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

//        String listName = "College";
//        String name = "new task";
//        String subtask = "new subtask";
//        String date = "date";
        tasks = new ArrayList<>();
//        Task mTask = new Task(name, subtask, date);
//        tasks.add(mTask);
//        TaskList newTaskList = new TaskList();
//        newTaskList.setTasks(tasks);
//        newTaskList.setListName(listName);
//        taskList.add(newTaskList);
//        recyclerTypeAdapter.notifyDataSetChanged();


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference favoritesRef = rootRef.child("users").child(userID).child("TaskList");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Object test = ds.getValue();
                    Log.d(TAG, "onDataChange: " + test);
                    TaskList newTaskList = ds.getValue(TaskList.class);
                    taskList.add(newTaskList);
                    recyclerTypeAdapter.notifyDataSetChanged();
                    String name = newTaskList.getTaskListName();
                    HashMap<String, HashMap<String, String>> test2 = newTaskList.getmHashMap();
                    Log.d(TAG, "onDataChange: " + name);
                    Log.d(TAG, "onDataChange: " + test2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        favoritesRef.addListenerForSingleValueEvent(eventListener);






//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds: dataSnapshot.getChildren()){
//                    TaskList mTaskList = ds.child(userID).child("TaskList").getValue(TaskList.class);
//                    String name = mTaskList.getTaskListName();
//                    Log.d(TAG, "onDataChange: " + name);
//                    HashMap<String, HashMap<String, HashMap<String, String>>> myHashMap = new HashMap<>();
//
//                    Log.d(TAG, "onDataChange: " + ds.child(userID).child("TaskList").getValue());

//                    Iterator it = mTaskList.getmHashMap().entrySet().iterator();
//                    while (it.hasNext()){
//                        Map.Entry pair = (Map.Entry)it.next();
//                        it.remove();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//    }

//    public void showData(DataSnapshot dataSnapshot){
//        for (DataSnapshot ds : dataSnapshot.child(userID).getChildren()){
//            TaskList newList = new TaskList();
//            newList.setListName(String.valueOf(ds.child("taskLists").getValue(TaskList.class)));
////            newList.setTasks((ds.child("taskLists").getValue(TaskList.class)));
//            Log.d(TAG, "showData: " + newList.getListName());

//            Object anotherTest = ds.child(userID);
//            Log.d(TAG, "showData: " + anotherTest);
//            HashMap testData = ds.child(userID).getValue();
//            Log.d(TAG, "showData: " + testData);
//            Object name = testData.get("College");
//            ArrayList<Object> mArray = new ArrayList<>();
//            mArray.add(testData);
//            String name = mArray.get(0);
//            TaskList newName = (TaskList)name;
//            Log.d(TAG, "showData: " + newName);

//            TaskList newTaskList = new TaskList(ds);
//            newTaskList = ds.child(userID).getValue(TaskList.class);
//            Log.d(TAG, "showData: " + taskList);
//            taskList.add(newTaskList);
//            String list_name = taskList.get(0).getListName();
//            Log.d(TAG, "showData: " + list_name);
//
////            TaskList newTaskList = new TaskList();
//            newTaskList.setListName(ds.child(userID).getValue(TaskList.class).getListName());
//            newTaskList.setTasks(ds.child(userID).getValue(TaskList.class).getTasks());

//            Object mObject = ds.child(userID).getValue();
////            Log.d(TAG, "showData: " + test);
//            ArrayList<Object> listOfTasks = new ArrayList<>();
//            listOfTasks.add(mObject);
//            Log.d(TAG, "showData: " + listOfTasks);
//            recyclerTypeAdapter.notifyDataSetChanged();
//        }
    }


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
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
            taskList = new ArrayList<TaskList>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            taskList = new ArrayList<>();
        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendInput(String input) {
        mInput = input;
        createNewList(mInput);
    }

    private void createNewList(String mInput){
        HashMap<String, String> taskMap = new HashMap<>();
        taskMap.put("task", "Sample Task");
        taskMap.put("subtask", "Sample Subtask");
        taskMap.put("date", "Sample Date");
        HashMap<String, HashMap<String, String>> nHashMap = new HashMap<>();
        nHashMap.put("Task1", taskMap);

        TaskList newTaskList = new TaskList(mInput, nHashMap);

        mDatabase.child("TaskList").child(mInput).setValue(newTaskList);
        recyclerTypeAdapter.notifyDataSetChanged();
        writeItems();
    }
}
