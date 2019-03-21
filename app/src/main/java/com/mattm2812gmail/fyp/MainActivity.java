package com.mattm2812gmail.fyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.graphics.Color;

import android.content.Intent;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login, signup;
    EditText username, password;
    int count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        username = findViewById(R.id.editText1);
        password = findViewById(R.id.editText2);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (username.getText().toString().equals("Matt") && password.getText().toString().equals("pass")){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));

                } else {
                    Toast.makeText(getApplicationContext(), "Wrong username/password", Toast.LENGTH_SHORT).show();

                    //only 3 login attempts
                    count--;
                    if (count==0){
                        login.setEnabled(false);
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
    }
}
