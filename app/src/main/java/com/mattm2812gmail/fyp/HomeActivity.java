package com.mattm2812gmail.fyp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements AddTaskList.OnInputListener{
    private static final String TAG = "HomeActivity";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;

    public ArrayList<Task> tasks;
    public ArrayList<TaskList> taskList;
    public HashMap<String, ArrayList<Task>> mHashMap;

    public RecyclerTypeAdapter recyclerTypeAdapter;

    public ListView lvItems;
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

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences mPrefs =  PreferenceManager.getDefaultSharedPreferences(this);
        this.isNightModeEnabled = mPrefs.getBoolean("night_mode", false);

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

        Button notif = findViewById(R.id.notification);
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });


        btnAddTask = findViewById(R.id.button);
        btnAddTaskList = findViewById(R.id.task_list_btn);

        mInputDisplay = findViewById(R.id.testView);

        mHashMap = new HashMap<>();

        taskList = new ArrayList<>();
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

        tasks = new ArrayList<>();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference favoritesRef = rootRef.child("users").child(userID).child("TaskList");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
        nHashMap.put("Task1", taskMap);

        // task list created containing the list name (mInput) and
        // and the previously created hashmap
        TaskList newTaskList = new TaskList(mInput, nHashMap);

        // map is pushed to the database
        mDatabase.child("TaskList").child(mInput).setValue(newTaskList);
        taskList.add(newTaskList);
        recyclerTypeAdapter.notifyDataSetChanged();
    }
    private void addNotification() {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.logo)
//                        .setContentTitle("Task")
//                        .setContentText("This is a test notification")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        Intent notificationIntent = new Intent(this, HomeActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//
//                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

    }


}
