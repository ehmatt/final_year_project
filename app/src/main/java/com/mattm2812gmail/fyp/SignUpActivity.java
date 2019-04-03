package com.mattm2812gmail.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
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

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;


    private Button btnSignup;
    private EditText getUsername, getPassword;
    private String email, password;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        btnSignup = findViewById(R.id.signup);
        getUsername = findViewById(R.id.enterName);
        getPassword = findViewById(R.id.enterPassword);

        email = getUsername.getText().toString().trim();
        password = getPassword.getText().toString().trim();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("60876280032-g0n0v92ikkglq9lno71lt7tu97ec4avu.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        progressDialog = new ProgressDialog(this);

        btnSignup.setOnClickListener(this);
        findViewById(R.id.googleSignIn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSignup){
            signup();
        }
        if (v.getId() == R.id.googleSignIn){
            signIn();
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

        }
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
