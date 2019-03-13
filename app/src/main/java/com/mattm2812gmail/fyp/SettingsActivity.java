package com.mattm2812gmail.fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.settings_container, new MySettingsFragment())
//                .commit();

    }

    public void logout(View view){
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));

    }
}
