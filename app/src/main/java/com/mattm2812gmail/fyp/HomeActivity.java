package com.mattm2812gmail.fyp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements AddTaskList.OnInputListener{

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;

    public ArrayList<Task> tasks;
    public ArrayList<TaskList> taskList;
    public HashMap<String, ArrayList<Task>> mHashMap;

    public RecyclerTypeAdapter recyclerTypeAdapter;

    public Button btnAddTask, btnAddTaskList;
    public String mInput;
    public TextView mInputDisplay;

    public String userID;
    public boolean isNightModeEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // shared preferences for dark mode
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        Map<String, ?> test = sharedpreferences.getAll();
        if (test.get("dark_mode") == "true"){
            ConstraintLayout home = findViewById(R.id.home_activity);
            home.setBackgroundColor(Color.DKGRAY);
        }

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

        btnAddTask = findViewById(R.id.button);
        btnAddTaskList = findViewById(R.id.task_list_btn);
        mInputDisplay = findViewById(R.id.testView);

        // hashmap to store tasks and arraylist to store lists of tasks
        mHashMap = new HashMap<>();
        taskList = new ArrayList<>();

        // setup recycler view
        RecyclerView recyclerView = findViewById(R.id.task_type_list);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerTypeAdapter = new RecyclerTypeAdapter(this, taskList);
        recyclerView.setAdapter(recyclerTypeAdapter);


        // drag and drop
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

        tasks = new ArrayList<>();

        // set task list from database when data is changed
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference favoritesRef = rootRef.child("users").child(userID).child("TaskList");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // iterate over "datasnapshot" ie the data at a certain point
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    TaskList newTaskList = ds.getValue(TaskList.class);
                    taskList.add(newTaskList);
                    recyclerTypeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        favoritesRef.addListenerForSingleValueEvent(eventListener);
    }

    public void addTaskList(View view){
        AddTaskList addTaskList = AddTaskList.newInstance();
        addTaskList.show(getSupportFragmentManager(),
                "add_task_list_dialog_fragment");
    }

    // MENU STUFF
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void sendInput(String input) {
        mInput = input;
        createNewList(mInput);
    }

    private void createNewList(String mInput){
        // sample task created and added to hashmap
        HashMap<String, String> taskMap = new HashMap<>();
        taskMap.put("task", "Sample Task");
        taskMap.put("subtask", "Sample Subtask");
        taskMap.put("date", "Sample Date");
        HashMap<String, HashMap<String, String>> nHashMap = new HashMap<>();
//        nHashMap.put("Task1", taskMap);

        // task list created containing the list name (mInput) and
        // and the previously created hashmap
        TaskList newTaskList = new TaskList(mInput, nHashMap);

        // map is pushed to the database
        mDatabase.child("TaskList").child(mInput).setValue(newTaskList);
        taskList.add(newTaskList);
        recyclerTypeAdapter.notifyDataSetChanged();
    }
}
