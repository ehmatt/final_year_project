package com.mattm2812gmail.fyp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.FirebaseApp;

public class App extends Application {

    private boolean isNightModeEnabled = false;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        SharedPreferences mPrefs =  PreferenceManager.getDefaultSharedPreferences(this);
        this.isNightModeEnabled = mPrefs.getBoolean("NIGHT_MODE", false);
    }

        public boolean isNightModeEnabled() {
            return isNightModeEnabled;
        }

        public void setIsNightModeEnabled(boolean isNightModeEnabled) {
            this.isNightModeEnabled = isNightModeEnabled;
        }
}
