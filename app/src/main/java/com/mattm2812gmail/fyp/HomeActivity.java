package com.mattm2812gmail.fyp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public ArrayList<String> items;
    public ArrayList<Task> tasks;
    public TaskListAdapter taskListAdapter;
    public ListView lvItems;
    public Button btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        btnAddTask = findViewById(R.id.button);
        btnAddTask.setTextColor(Color.WHITE);

        String mUsername = "Matt";
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

//        lvItems = findViewById(R.id.list_todo);

//        items = new ArrayList<String>();
//        readItems();
//        itemsAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, items);
//        lvItems.setAdapter(itemsAdapter);

//
//        tasks = new ArrayList<Task>();
//        readItems();
//        TaskListAdapter taskListAdapter = new TaskListAdapter(this, R.layout.task_view_layout, tasks);
//        lvItems.setAdapter(taskListAdapter);
//        setupListViewListener();
    }

    public void addTask(View view){
//        // open menu then add task
        AddTask addTaskBottomDialogFragment = AddTask.newInstance();
        addTaskBottomDialogFragment.show(getSupportFragmentManager(),
                "add_task_dialog_fragment");

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
}
