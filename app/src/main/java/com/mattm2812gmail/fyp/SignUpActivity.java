package com.mattm2812gmail.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    Button login, signup;
    EditText username, password, confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        signup = findViewById(R.id.signup);
        username = findViewById(R.id.enterName);
        password = findViewById(R.id.enterPassword);
        confirmPass = findViewById(R.id.confirmPassword);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password != confirmPass){
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // create new user
                    // and redirect to homepage
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                }
            }
        });

    }
}
