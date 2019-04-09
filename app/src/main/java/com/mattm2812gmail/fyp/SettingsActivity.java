package com.mattm2812gmail.fyp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;
    private Button logout;
    public static boolean isDarkModeEnabled;

    private Switch darkMode, notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        final SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Settings");


        darkMode = findViewById(R.id.dark_mode);
        notifications = findViewById(R.id.notifications);
        darkMode.setTextColor(Color.BLACK);
        notifications.setTextColor(Color.BLACK);

        // swap to dark mode when switch is clicked
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    ConstraintLayout settings = findViewById(R.id.settings_activity);
                    settings.setBackgroundColor(Color.DKGRAY);
                    darkMode.setTextColor(Color.WHITE);
                    notifications.setTextColor(Color.WHITE);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("dark_mode", "true");
                    editor.commit();

//                    recreate();
                }else if (isChecked == false){
                    ConstraintLayout settings = findViewById(R.id.settings_activity);
                    settings.setBackgroundColor(Color.WHITE);
                    darkMode.setTextColor(Color.BLACK);
                    notifications.setTextColor(Color.BLACK);
//                    recreate();
                }
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mFirebaseAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
