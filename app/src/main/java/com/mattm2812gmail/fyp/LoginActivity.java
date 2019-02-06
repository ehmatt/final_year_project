package com.mattm2812gmail.fyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText username, password;
    TextView t1;
    int count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        login = (Button)findViewById(R.id.button);
        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);
        t1 = (TextView) findViewById(R.id.textView);

        t1.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (username.toString().equals("matt") && password.toString().equals("password")){
                    //redirect to homepage

                } else {
                    Toast.makeText(getApplicationContext(), "Wrong username/password", Toast.LENGTH_SHORT).show();
                    t1.setVisibility(View.VISIBLE);
                    t1.setBackgroundColor(Color.RED);

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
