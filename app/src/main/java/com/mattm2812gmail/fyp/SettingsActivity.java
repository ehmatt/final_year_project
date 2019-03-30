package com.mattm2812gmail.fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Settings");


        mFirebaseAuth = FirebaseAuth.getInstance();

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);



//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.settings_container, new MySettingsFragment())
//                .commit();

    }

    @Override
    public void onClick(View v) {
        mFirebaseAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
