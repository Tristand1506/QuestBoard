package com.sleeplessstudios.mappapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    private ImageButton signUp;
    private EditText username;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //button listeners
        signUp = findViewById(R.id.signup_btn);
        signUp.setOnClickListener(v -> openMapScreen());

        username = findViewById(R.id.signup_username_txt);
        email = findViewById(R.id.signup_email_txt);
        password = findViewById(R.id.signup_password_txt);
    }

    public void openMapScreen()
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}