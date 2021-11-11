package com.sleeplessstudios.mappapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ObjectLib.UserAccount;
import UtilLib.AccountManager;

public class SignUp extends AppCompatActivity {
    private ImageButton signUp;
    private EditText username;
    private EditText email;
    private EditText password;

    FirebaseAuth mAuth;

    private String TAG = "Activity_SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Firebase Init
        mAuth = FirebaseAuth.getInstance();

        //button listeners
        signUp = findViewById(R.id.signup_btn);
        signUp.setOnClickListener(v -> SignUpAccount(email.getText().toString(), password.getText().toString(), username.getText().toString()));

        username = findViewById(R.id.signup_username_txt);
        email = findViewById(R.id.signup_email_txt);
        password = findViewById(R.id.signup_password_txt);
    }

    private void SignUpAccount(String email , String password, String username){
        if (email.isEmpty()||password.isEmpty()||username.isEmpty()){
            Toast.makeText(SignUp.this, "Please enter a email, username and password",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            UserAccount in = new UserAccount(username,email);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //create new userData
                                Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                AccountManager.addUser(in);
                                openMapScreen();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void openMapScreen()
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}