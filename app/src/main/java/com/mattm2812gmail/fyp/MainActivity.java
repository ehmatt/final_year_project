package com.mattm2812gmail.fyp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private Button login;
    private TextView signup;
    private EditText etUsername, etPassword;
    private String username, password;
    private ProgressDialog progressDialog;

    public MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        if(mFirebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        etUsername = findViewById(R.id.editText1);
        etPassword = findViewById(R.id.editText2);

        progressDialog = new ProgressDialog(this);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == login){
            login();
        }

        if(view == signup){
            finish();
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        }
    }

    private void login(){
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        mFirebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}