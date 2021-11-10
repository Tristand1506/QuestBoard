package com.sleeplessstudios.mappapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    private ImageButton signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUp = findViewById(R.id.signup_btn);
        signUp.setOnClickListener(v -> openMapScreen());

    }

    public void openMapScreen()
    {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }
}