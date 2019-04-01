package com.mattm2812gmail.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;

    private Button btnSignup;
    private EditText getUsername, getPassword;
    private String email, password;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnSignup = findViewById(R.id.signup);
        getUsername = findViewById(R.id.enterName);
        getPassword = findViewById(R.id.enterPassword);

        email = getUsername.getText().toString().trim();
        password = getPassword.getText().toString().trim();


        progressDialog = new ProgressDialog(this);

        btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSignup){
            signup();
        }
    }

    public void signup(){
        progressDialog.setMessage("Signing up...");
        progressDialog.show();

        email = getUsername.getText().toString().trim();
        password = getPassword.getText().toString().trim();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    onAuthSuccess(task.getResult().getUser());
                }else{
                    Toast.makeText(SignUpActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onAuthSuccess(FirebaseUser user){
        writeNewUser(user.getUid(), user.getEmail());
        finish();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    public void writeNewUser(String uID, String email){
        User user = new User(email);
        mDatabase.child("users").child(uID).setValue(user);
    }
}
