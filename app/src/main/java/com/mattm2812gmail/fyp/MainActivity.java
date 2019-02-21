package com.mattm2812gmail.fyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;

import android.content.Intent;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText username, password;
//    TextView t1;
    int count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button)findViewById(R.id.button);
        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (username.getText().toString().equals("matt") && password.getText().toString().equals("password")){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));

                } else {
                    Toast.makeText(getApplicationContext(), "Wrong username/password", Toast.LENGTH_SHORT).show();
//                    t1.setVisibility(View.VISIBLE);
//                    t1.setBackgroundColor(Color.RED);

                    //only 3 login attempts
                    count--;
                    if (count==0){
                        login.setEnabled(false);
                    }
                }
            }
        });
    }
}
