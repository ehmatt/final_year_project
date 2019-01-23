package com.mattm2812gmail.fyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public void login(View view){


        //only 3 login attempts
        int x = 3;
        x--;
        if (x==0){
            //disable
        }
    }
}
